package com.centro.weg_safety.application.dto.request.usuario;

import com.centro.weg_safety.domain.model.enums.usuario.StatusUsuario;
import jakarta.validation.constraints.NotNull;

public record AlterarStatusUsuarioRequest(
        @NotNull StatusUsuario status
) {
}
