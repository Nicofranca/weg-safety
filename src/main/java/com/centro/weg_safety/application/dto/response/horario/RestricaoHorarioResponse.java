package com.centro.weg_safety.application.dto.response.horario;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record RestricaoHorarioResponse(
        UUID id,
        List<String> dias,
        LocalTime horaInicio,
        LocalTime horaFim,
        String perfil
) {
}
