package com.centro.weg_safety.application.dto.response.usuario;

import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioResumoResponse(
        UUID id,
        String nome,
        String email,
        String status,
        LocalDateTime ultimoAcesso
) {
}
