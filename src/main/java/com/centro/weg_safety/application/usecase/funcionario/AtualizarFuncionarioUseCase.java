package com.centro.weg_safety.application.usecase.funcionario;

import com.centro.weg_safety.application.dto.request.funcionario.AtualizarFuncionarioRequest;
import com.centro.weg_safety.application.dto.response.funcionario.FuncionarioResponse;
import com.centro.weg_safety.application.mapper.FuncionarioMapper;
import com.centro.weg_safety.domain.exception.RecursoNaoEncontradoException;
import com.centro.weg_safety.domain.model.Funcionario;
import com.centro.weg_safety.infra.persistence.jpa.CargoRepository;
import com.centro.weg_safety.infra.persistence.jpa.FuncionarioRepository;
import com.centro.weg_safety.infra.persistence.jpa.SetorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AtualizarFuncionarioUseCase {

    private final FuncionarioRepository funcionarioRepository;
    private final CargoRepository cargoRepository;
    private final SetorRepository setorRepository;
    private final FuncionarioMapper funcionarioMapper;

    public AtualizarFuncionarioUseCase(FuncionarioRepository funcionarioRepository,
                                       CargoRepository cargoRepository,
                                       SetorRepository setorRepository,
                                       FuncionarioMapper funcionarioMapper) {
        this.funcionarioRepository = funcionarioRepository;
        this.cargoRepository = cargoRepository;
        this.setorRepository = setorRepository;
        this.funcionarioMapper = funcionarioMapper;
    }

    @Transactional
    public FuncionarioResponse execute(UUID id, AtualizarFuncionarioRequest request) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado"));

        if (request.nome() != null) funcionario.setNome(request.nome());
        if (request.cpf() != null) funcionario.setCpf(request.cpf());
        if (request.rg() != null) funcionario.setRg(request.rg());
        if (request.email() != null) funcionario.setEmail(request.email());
        if (request.dataNascimento() != null) funcionario.setDataNascimento(request.dataNascimento());
        if (request.genero() != null) funcionario.setGenero(request.genero());
        if (request.dataAdmissao() != null) funcionario.setDataAdmissao(request.dataAdmissao());
        if (request.turno() != null) funcionario.setTurno(request.turno());

        if (request.cargoId() != null) {
            funcionario.setCargo(cargoRepository.findById(request.cargoId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Cargo não encontrado")));
        }
        if (request.setorId() != null) {
            funcionario.setSetor(setorRepository.findById(request.setorId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Setor não encontrado")));
        }

        funcionario.setDataAtualizacao(LocalDateTime.now());
        funcionario = funcionarioRepository.save(funcionario);

        return funcionarioMapper.toResponse(funcionario);
    }
}
