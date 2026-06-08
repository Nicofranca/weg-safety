package com.centro.weg_safety.application.usecase.funcionario;

import com.centro.weg_safety.application.dto.response.funcionario.FuncionarioResponse;
import com.centro.weg_safety.application.mapper.FuncionarioMapper;
import com.centro.weg_safety.domain.exception.RecursoNaoEncontradoException;
import com.centro.weg_safety.infra.persistence.jpa.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BuscarFuncionarioUseCase {

    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioMapper funcionarioMapper;

    public BuscarFuncionarioUseCase(FuncionarioRepository funcionarioRepository, FuncionarioMapper funcionarioMapper) {
        this.funcionarioRepository = funcionarioRepository;
        this.funcionarioMapper = funcionarioMapper;
    }

    public FuncionarioResponse execute(UUID id) {
        return funcionarioRepository.findById(id)
                .map(funcionarioMapper::toResponse)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado"));
    }
}
