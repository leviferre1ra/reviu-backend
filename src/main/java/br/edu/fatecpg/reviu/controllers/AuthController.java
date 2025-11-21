package br.edu.fatecpg.reviu.controllers;

import br.edu.fatecpg.reviu.domain.user.User;
import br.edu.fatecpg.reviu.dto.*;
import br.edu.fatecpg.reviu.infra.security.TokenService;
import br.edu.fatecpg.reviu.repositories.UserRepository;
import br.edu.fatecpg.reviu.services.AuthService;
import br.edu.fatecpg.reviu.services.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        User user = this.userRepository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())){
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new LoginAndRegisterResponseDTO(user.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterRequestDTO body){
        Optional<User> user = this.userRepository.findByEmail(body.email());
        if (user.isEmpty()) {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setName(body.name());
            newUser.setUsername(body.username());

            String code = authService.generateVerificationCode();
            newUser.setVerificationCode(code);
            newUser.setVerificationExpiry(Instant.now().plus(1, ChronoUnit.HOURS));
            newUser.setVerified(false);

            this.userRepository.save(newUser);
            emailService.sendVerificationEmail(newUser);
            return ResponseEntity.ok("Verifique seu e-mail");
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestBody VerifyCodeDTO body) {
        Optional<User> optionalUser = userRepository.findByVerificationCode(body.code());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Código inválido.");
        }

        User user = optionalUser.get();

        if (user.getVerified()) {
            return ResponseEntity.ok("Usuário já verificado.");
        }

        if (user.getVerificationExpiry() != null &&
                Instant.now().isAfter(user.getVerificationExpiry())) {
            return ResponseEntity.badRequest().body("Código expirado. Solicite um novo.");
        }

        user.setVerified(true);
        user.setVerificationCode(null);
        user.setVerificationExpiry(null);
        userRepository.save(user);

        String token = this.tokenService.generateToken(user);
        return ResponseEntity.ok(new LoginAndRegisterResponseDTO(user.getName(), token));
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<?> resendVerification(@RequestBody EmailRequestDTO body) {
        Optional<User> optionalUser = userRepository.findByEmail(body.email());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }

        User user = optionalUser.get();

        if (user.getVerified()) {
            return ResponseEntity.badRequest().body("Usuário já verificado.");
        }

        String newCode = authService.generateVerificationCode();
        user.setVerificationCode(newCode);
        user.setVerificationExpiry(Instant.now().plus(1, ChronoUnit.HOURS));
        userRepository.save(user);

        emailService.sendVerificationEmail(user);

        return ResponseEntity.ok("Novo código enviado para seu e-mail.");
    }
}
