package com.centro.weg_safety.application.dto.request.permissao;

import com.centro.weg_safety.domain.model.enums.permissaoAcesso.TipoPermissao;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AdicionarPermissaoRequest(
        @NotNull UUID funcionarioId,
        @NotNull TipoPermissao tipo,
        String motivo
) {
}
