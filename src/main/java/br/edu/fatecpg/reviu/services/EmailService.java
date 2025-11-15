package br.edu.fatecpg.reviu.services;

import br.edu.fatecpg.reviu.domain.user.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String fromAddress = "thiiago.allmeida44@gmail.com";

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendVerificationEmail(User user) {
        String subject = "Verifique seu e-mail - Reviu";
        String text = "Olá " + user.getName() + ",\n\n" +
                "Obrigado por se cadastrar! Por favor confirme seu e-mail usando o código abaixo:\n\n" +
                user.getVerificationCode() + "\n\n" +
                "Este código expira em 1 hora.\n\n" +
                "Abraços,\nEquipe Reviu";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(user.getEmail());
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}
