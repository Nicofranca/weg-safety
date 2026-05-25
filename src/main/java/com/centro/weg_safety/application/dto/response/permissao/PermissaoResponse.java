package com.centro.weg_safety.application.dto.response.permissao;

import java.util.UUID;

public record PermissaoResponse(
        UUID id,
        UUID funcionarioId,
        String funcionarioNome,
        String funcionarioMatricula,
        String tipo,
        String motivo
) {
}
