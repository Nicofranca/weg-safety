package com.centro.weg_safety.application.dto.response.cargo;

import java.util.UUID;

public record CargoResponse(
        UUID id,
        String nome,
        Boolean ativo
) {
}
