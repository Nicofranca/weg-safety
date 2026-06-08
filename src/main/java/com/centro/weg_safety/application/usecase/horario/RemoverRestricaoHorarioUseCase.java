package com.centro.weg_safety.application.usecase.horario;

import com.centro.weg_safety.domain.exception.RecursoNaoEncontradoException;
import com.centro.weg_safety.infra.persistence.jpa.RestricaoHorarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RemoverRestricaoHorarioUseCase {

    private final RestricaoHorarioRepository restricaoRepository;

    public RemoverRestricaoHorarioUseCase(RestricaoHorarioRepository restricaoRepository) {
        this.restricaoRepository = restricaoRepository;
    }

    @Transactional
    public void execute(UUID id) {
        if (!restricaoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Restrição de horário não encontrada");
        }
        restricaoRepository.deleteById(id);
    }
}
