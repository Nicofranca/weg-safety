package com.centro.weg_safety.application.dto.response.cracha;

import java.time.LocalDate;
import java.util.UUID;

public record ValidarCrachaResponse(
        Boolean valido,
        UUID funcionarioId,
        String funcionarioNome,
        String funcionarioStatus,
        String perfilAcesso,
        LocalDate validade,
        String crachaStatus
) {
}
