package com.centro.weg_safety.application.dto.response.log;

import com.centro.weg_safety.application.dto.response.area.AreaResumoResponse;
import com.centro.weg_safety.application.dto.response.funcionario.FuncionarioResumoResponse;

import java.time.LocalDateTime;
import java.util.UUID;

public record LogAcessoDetailResponse(
        UUID id,
        FuncionarioResumoResponse funcionario,
        AreaResumoResponse area,
        String resultado,
        String motivo,
        LocalDateTime tsEvento
) {
}
