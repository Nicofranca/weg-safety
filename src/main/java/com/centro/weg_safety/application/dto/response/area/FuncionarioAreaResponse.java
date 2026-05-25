package com.centro.weg_safety.application.dto.response.area;

import java.util.UUID;

public record FuncionarioAreaResponse(
        UUID id,
        String nome,
        String matricula,
        String cargo,
        String tipoPermissao,
        Boolean episValidos
) {
}
