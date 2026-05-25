package com.centro.weg_safety.application.dto.request.cracha;

import com.centro.weg_safety.domain.model.enums.cracha.PerfilAcesso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record EmitirCrachaRequest(
        @NotNull UUID funcionarioId,
        @NotBlank String uidRfid,
        @NotNull LocalDate validade,
        @NotNull PerfilAcesso perfilAcesso
        ) {
}
