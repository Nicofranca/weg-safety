package com.centro.weg_safety.application.usecase.permissao;

import com.centro.weg_safety.domain.exception.RecursoNaoEncontradoException;
import com.centro.weg_safety.infra.persistence.jpa.PermissaoAcessoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RemoverPermissaoUseCase {

    private final PermissaoAcessoRepository permissaoRepository;

    public RemoverPermissaoUseCase(PermissaoAcessoRepository permissaoRepository) {
        this.permissaoRepository = permissaoRepository;
    }

    @Transactional
    public void execute(UUID id) {
        if (!permissaoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Permissão não encontrada");
        }
        permissaoRepository.deleteById(id);
    }
}
