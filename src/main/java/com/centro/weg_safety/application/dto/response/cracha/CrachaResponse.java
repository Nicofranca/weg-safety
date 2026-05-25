package com.centro.weg_safety.application.dto.response.cracha;

import com.centro.weg_safety.application.dto.response.funcionario.FuncionarioResumoResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record CrachaResponse(
        UUID id,
        String uidRfid ,
        LocalDate validade,
        String perfilAcesso,
        String status,
        LocalDateTime dataEmissao,
        FuncionarioResumoResponse funcionario
) {
}
