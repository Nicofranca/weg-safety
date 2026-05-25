package com.centro.weg_safety.application.dto.response.log;

import java.time.LocalDateTime;
import java.util.UUID;

public record LogAcessoResponse(
        UUID id,
        String funcionarioNome,
        String funcionarioMatricula,
        String areaNome,
        String resultado,
        String motivo,
        LocalDateTime tsEvento
) {
}
