package com.centro.weg_safety.application.usecase.cracha;

import com.centro.weg_safety.application.dto.request.cracha.EmitirCrachaRequest;
import com.centro.weg_safety.application.dto.response.cracha.EmitirCrachaResponse;
import com.centro.weg_safety.domain.exception.ConflitoException;
import com.centro.weg_safety.domain.exception.RecursoNaoEncontradoException;
import com.centro.weg_safety.domain.model.Cracha;
import com.centro.weg_safety.domain.model.enums.cracha.StatusCracha;
import com.centro.weg_safety.infra.persistence.jpa.CrachaRepository;
import com.centro.weg_safety.infra.persistence.jpa.FuncionarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class EmitirCrachaUseCase {

    private final CrachaRepository crachaRepository;
    private final FuncionarioRepository funcionarioRepository;

    public EmitirCrachaUseCase(CrachaRepository crachaRepository, FuncionarioRepository funcionarioRepository) {
        this.crachaRepository = crachaRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    @Transactional
    public EmitirCrachaResponse execute(EmitirCrachaRequest request) {
        if (crachaRepository.findByUidRfid(request.uidRfid()).isPresent()) {
            throw new ConflitoException("UID RFID já cadastrado");
        }

        var funcionario = funcionarioRepository.findById(request.funcionarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado"));

        crachaRepository.findByFuncionarioIdAndStatus(request.funcionarioId(), StatusCracha.ATIVO)
                .ifPresent(anterior -> {
                    anterior.setStatus(StatusCracha.INATIVO);
                    crachaRepository.save(anterior);
                });

        Cracha novo = Cracha.builder()
                .funcionario(funcionario)
                .uidRfid(request.uidRfid())
                .validade(request.validade())
                .perfilAcesso(request.perfilAcesso())
                .status(StatusCracha.ATIVO)
                .dataEmissao(LocalDateTime.now())
                .build();

        novo = crachaRepository.save(novo);

        return new EmitirCrachaResponse(novo.getId(), "Crachá emitido com sucesso");
    }
}
