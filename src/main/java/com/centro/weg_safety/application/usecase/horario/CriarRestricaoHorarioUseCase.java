package com.centro.weg_safety.application.usecase.horario;

import com.centro.weg_safety.application.dto.request.horario.RestricaoHorarioRequest;
import com.centro.weg_safety.application.dto.response.horario.RestricaoHorarioResponse;
import com.centro.weg_safety.application.mapper.HorarioMapper;
import com.centro.weg_safety.domain.exception.RecursoNaoEncontradoException;
import com.centro.weg_safety.domain.model.RestricaoHorario;
import com.centro.weg_safety.infra.persistence.jpa.AreaRepository;
import com.centro.weg_safety.infra.persistence.jpa.RestricaoHorarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CriarRestricaoHorarioUseCase {

    private final RestricaoHorarioRepository restricaoRepository;
    private final AreaRepository areaRepository;
    private final HorarioMapper horarioMapper;

    public CriarRestricaoHorarioUseCase(RestricaoHorarioRepository restricaoRepository,
                                        AreaRepository areaRepository,
                                        HorarioMapper horarioMapper) {
        this.restricaoRepository = restricaoRepository;
        this.areaRepository = areaRepository;
        this.horarioMapper = horarioMapper;
    }

    @Transactional
    public RestricaoHorarioResponse execute(UUID areaId, RestricaoHorarioRequest request) {
        var area = areaRepository.findById(areaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Área não encontrada"));

        RestricaoHorario restricao = RestricaoHorario.builder()
                .area(area)
                .dias(request.dias())
                .horaInicio(request.horaInicio())
                .horaFim(request.horaFim())
                .perfil(request.perfil())
                .dataCriacao(LocalDateTime.now())
                .build();

        restricao = restricaoRepository.save(restricao);
        return horarioMapper.toResponse(restricao);
    }
}
