package com.centro.weg_safety.application.dto.response.usuario;

import java.util.UUID;

public record CriarUsuarioResponse(
        UUID id,
        String message

) {
}
