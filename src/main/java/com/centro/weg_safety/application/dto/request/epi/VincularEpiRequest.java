package com.centro.weg_safety.application.dto.request.epi;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record VincularEpiRequest(
        @NotNull UUID funcionarioId,
        @NotNull UUID epiTipoId,
        @NotBlank String nrCa,
        @NotNull LocalDate dataEntrega,
        @NotNull LocalDate dataValidade
) {
}
