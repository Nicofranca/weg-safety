package com.centro.weg_safety.application.dto.response.funcionario;

import com.centro.weg_safety.application.dto.response.cracha.CrachaResumoResponse;

import java.util.UUID;

public record FuncionarioResumoResponse(
        UUID id,
        String nome,
        String matricula,
        String cargo,
        String setor,
        String status,
        CrachaResumoResponse cracha
) {
}
