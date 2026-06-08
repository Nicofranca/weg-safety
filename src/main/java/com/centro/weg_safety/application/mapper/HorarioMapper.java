package com.centro.weg_safety.application.mapper;

import com.centro.weg_safety.application.dto.response.horario.RestricaoHorarioResponse;
import com.centro.weg_safety.domain.model.RestricaoHorario;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class HorarioMapper {

    public RestricaoHorarioResponse toResponse(RestricaoHorario r) {
        return new RestricaoHorarioResponse(
                r.getId(),
                r.getDias() != null ? r.getDias().stream().map(Enum::name).toList() : Collections.emptyList(),
                r.getHoraInicio(),
                r.getHoraFim(),
                r.getPerfil()
        );
    }
}
