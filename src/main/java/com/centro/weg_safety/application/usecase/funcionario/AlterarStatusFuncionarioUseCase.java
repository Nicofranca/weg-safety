package com.centro.weg_safety.application.usecase.funcionario;

import com.centro.weg_safety.application.dto.request.funcionario.AlterarStatusFuncionarioRequest;
import com.centro.weg_safety.domain.exception.RecursoNaoEncontradoException;
import com.centro.weg_safety.domain.model.Cracha;
import com.centro.weg_safety.domain.model.Funcionario;
import com.centro.weg_safety.domain.model.enums.cracha.StatusCracha;
import com.centro.weg_safety.domain.model.enums.funcionario.StatusFuncionario;
import com.centro.weg_safety.infra.persistence.jpa.CrachaRepository;
import com.centro.weg_safety.infra.persistence.jpa.FuncionarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AlterarStatusFuncionarioUseCase {

    private final FuncionarioRepository funcionarioRepository;
    private final CrachaRepository crachaRepository;

    public AlterarStatusFuncionarioUseCase(FuncionarioRepository funcionarioRepository, CrachaRepository crachaRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.crachaRepository = crachaRepository;
    }

    @Transactional
    public void execute(UUID id, AlterarStatusFuncionarioRequest request) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado"));

        funcionario.setStatus(request.status());

        if (request.status() == StatusFuncionario.INATIVO) {
            List<Cracha> crachasAtivos = crachaRepository.findByFuncionarioId(id)
                    .stream()
                    .filter(c -> c.getStatus() == StatusCracha.ATIVO)
                    .toList();

            crachasAtivos.forEach(c -> {
                c.setStatus(StatusCracha.INATIVO);
                crachaRepository.save(c);
            });
        }

        funcionarioRepository.save(funcionario);
    }
}
