package com.centro.weg_safety.application.dto.request.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AtualizarUsuarioRequest(
        String nome,
        @Email String email
) {
}
