package com.centro.weg_safety.application.dto.response.epi;

import java.time.LocalDate;
import java.util.UUID;

public record EpiVencimentoResponse(
        UUID epiFuncionarioId,
        UUID funcionarioId,
        String funcionarioNome,
        String funcionarioMatricula,
        String epiTipo,
        String nrCa,
        LocalDate dataValidade,
        Integer diasParaVencer,
        String status
) {
}
