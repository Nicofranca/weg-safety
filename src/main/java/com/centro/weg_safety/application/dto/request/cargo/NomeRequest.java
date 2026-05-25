package com.centro.weg_safety.application.dto.request.cargo;

import jakarta.validation.constraints.NotBlank;

public record NomeRequest(
        @NotBlank String nome
) {
}
