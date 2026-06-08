package com.centro.weg_safety.application.usecase.horario;

import com.centro.weg_safety.application.dto.request.horario.RestricaoHorarioRequest;
import com.centro.weg_safety.application.dto.response.horario.RestricaoHorarioResponse;
import com.centro.weg_safety.application.mapper.HorarioMapper;
import com.centro.weg_safety.domain.exception.RecursoNaoEncontradoException;
import com.centro.weg_safety.infra.persistence.jpa.RestricaoHorarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AtualizarRestricaoHorarioUseCase {

    private final RestricaoHorarioRepository restricaoRepository;
    private final HorarioMapper horarioMapper;

    public AtualizarRestricaoHorarioUseCase(RestricaoHorarioRepository restricaoRepository, HorarioMapper horarioMapper) {
        this.restricaoRepository = restricaoRepository;
        this.horarioMapper = horarioMapper;
    }

    @Transactional
    public RestricaoHorarioResponse execute(UUID id, RestricaoHorarioRequest request) {
        var restricao = restricaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Restrição de horário não encontrada"));

        if (request.dias() != null) restricao.setDias(request.dias());
        if (request.horaInicio() != null) restricao.setHoraInicio(request.horaInicio());
        if (request.horaFim() != null) restricao.setHoraFim(request.horaFim());
        if (request.perfil() != null) restricao.setPerfil(request.perfil());

        restricao = restricaoRepository.save(restricao);
        return horarioMapper.toResponse(restricao);
    }
}
