package com.centro.weg_safety.application.dto.response.epi;

import java.time.LocalDate;
import java.util.UUID;

public record EpiFuncionarioResponse(
        UUID id,
        EpiTipoResponse epiTipo,
        String nrCa,
        LocalDate dataEntrega,
        LocalDate dataValidade,
        Integer diasParaVencer,
        String status
) {
}
