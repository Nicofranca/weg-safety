package com.centro.weg_safety.application.usecase.cracha;

import com.centro.weg_safety.domain.exception.RecursoNaoEncontradoException;
import com.centro.weg_safety.domain.model.enums.cracha.StatusCracha;
import com.centro.weg_safety.infra.persistence.jpa.CrachaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class InvalidarCrachaUseCase {

    private final CrachaRepository crachaRepository;

    public InvalidarCrachaUseCase(CrachaRepository crachaRepository) {
        this.crachaRepository = crachaRepository;
    }

    @Transactional
    public void execute(UUID id) {
        var cracha = crachaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Crachá não encontrado"));
        cracha.setStatus(StatusCracha.INATIVO);
        crachaRepository.save(cracha);
    }
}
