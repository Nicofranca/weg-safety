package com.centro.weg_safety.application.dto.response.setor;

import java.util.UUID;

public record SetorResponse(
        UUID id,
        String nome,
        Boolean ativo
) {
}
