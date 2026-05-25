package com.centro.weg_safety.application.dto.response.cracha;

import java.time.LocalDate;

public record CrachaResumoResponse(
        String uidRfid,
        LocalDate validade,
        String status
) {
}
