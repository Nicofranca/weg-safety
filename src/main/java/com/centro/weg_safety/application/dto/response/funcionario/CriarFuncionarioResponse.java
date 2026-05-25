package com.centro.weg_safety.application.dto.response.funcionario;

import java.util.UUID;

public record CriarFuncionarioResponse(
        UUID id,
        String matricula,
        String message
) {
}
