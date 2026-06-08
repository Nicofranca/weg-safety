package com.centro.weg_safety.application.usecase.epi;

import com.centro.weg_safety.domain.exception.RecursoNaoEncontradoException;
import com.centro.weg_safety.infra.persistence.jpa.EpiFuncionarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RemoverEpiUseCase {

    private final EpiFuncionarioRepository epiFuncionarioRepository;

    public RemoverEpiUseCase(EpiFuncionarioRepository epiFuncionarioRepository) {
        this.epiFuncionarioRepository = epiFuncionarioRepository;
    }

    @Transactional
    public void execute(UUID id) {
        if (!epiFuncionarioRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("EPI não encontrado");
        }
        epiFuncionarioRepository.deleteById(id);
    }
}
