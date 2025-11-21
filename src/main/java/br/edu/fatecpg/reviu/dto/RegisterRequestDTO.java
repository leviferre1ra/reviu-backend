package br.edu.fatecpg.reviu.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(String name,
                                 String username,
                                 String email,

                                 @Size(min = 8, message = "A nova senha deve ter no mínimo 8 caracteres.")
                                 @Pattern(
                                         regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&.])[A-Za-z\\d@$!%*#?&.]{8,}$",
                                         message = "A senha deve conter letras, números e caracteres especiais."
                                 )
                                 String password) {
}
