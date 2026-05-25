package com.centro.weg_safety.application.dto.request.area;

import com.centro.weg_safety.domain.model.enums.area.StatusArea;
import jakarta.validation.constraints.NotNull;

public record AlterarStatusAreaRequest(
        @NotNull StatusArea status
) {
}
