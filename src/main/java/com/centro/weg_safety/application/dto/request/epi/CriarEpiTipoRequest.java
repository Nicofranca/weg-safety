package com.centro.weg_safety.application.dto.request.epi;

import jakarta.validation.constraints.NotBlank;

public record CriarEpiTipoRequest(
        @NotBlank String nome,
        String descricao
) {
}
