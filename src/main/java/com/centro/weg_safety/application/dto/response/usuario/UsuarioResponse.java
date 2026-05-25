package com.centro.weg_safety.application.dto.response.usuario;

import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioResponse(
        UUID id,
        String nome,
        String email,
        String status,
        Integer tentativasLogin,
        LocalDateTime bloqueadoAte,
        LocalDateTime ultimoAcesso,
        LocalDateTime dataCriacao
) {
}
