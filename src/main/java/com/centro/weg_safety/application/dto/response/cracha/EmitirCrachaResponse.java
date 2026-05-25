package com.centro.weg_safety.application.dto.response.cracha;

import java.util.UUID;

public record EmitirCrachaResponse(
        UUID id,
        String message
) {
}
