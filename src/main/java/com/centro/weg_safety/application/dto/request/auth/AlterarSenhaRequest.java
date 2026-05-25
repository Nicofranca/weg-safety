package com.centro.weg_safety.application.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AlterarSenhaRequest(
        @NotBlank String senhaAtual,
        @NotBlank @Size(min = 8) String novaSenha,
        @NotBlank String confirmacaoSenha
) {
}
