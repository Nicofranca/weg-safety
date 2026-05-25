package com.centro.weg_safety.application.dto.response.area;

import java.util.List;
import java.util.UUID;

public record AreaResumoResponse(
        UUID id,
        String nome,
        String codigo,
        String tipo,
        String nivelRisco,
        String status,
        Integer totalAutorizados,
        List<String> episObrigatorios
) {
}
