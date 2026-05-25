package com.centro.weg_safety.application.dto.response.auth;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record LoginResponse(
        String token,
        String nome,
        String email,
        LocalDateTime expiraEm
) {
}
