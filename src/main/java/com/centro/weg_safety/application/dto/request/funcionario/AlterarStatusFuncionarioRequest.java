package com.centro.weg_safety.application.dto.request.funcionario;

import com.centro.weg_safety.domain.model.enums.funcionario.StatusFuncionario;
import jakarta.validation.constraints.NotNull;

public record AlterarStatusFuncionarioRequest(
        @NotNull StatusFuncionario status
) {
}
