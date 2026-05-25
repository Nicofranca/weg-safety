package com.centro.weg_safety.application.dto.response.epi;

import java.util.UUID;

public record EpiTipoResponse(
        UUID id,
        String nome,
        String descricao,
        Boolean ativo
) {
}
