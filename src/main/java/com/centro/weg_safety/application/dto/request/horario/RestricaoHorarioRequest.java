package com.centro.weg_safety.application.dto.request.horario;

import com.centro.weg_safety.domain.model.enums.restricaoHorario.DiaSemana;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.List;

public record RestricaoHorarioRequest(
        @NotEmpty List<DiaSemana> dias,
        @NotNull LocalTime horaInicio,
        @NotNull LocalTime horaFim,
        @NotBlank String perfil
) {
}