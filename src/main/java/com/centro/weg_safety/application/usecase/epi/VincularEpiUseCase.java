package com.centro.weg_safety.application.usecase.epi;

import com.centro.weg_safety.application.dto.request.epi.VincularEpiRequest;
import com.centro.weg_safety.application.dto.response.epi.VincularEpiResponse;
import com.centro.weg_safety.domain.exception.RecursoNaoEncontradoException;
import com.centro.weg_safety.domain.model.EpiFuncionario;
import com.centro.weg_safety.domain.model.enums.epiFuncionario.StatusEpi;
import com.centro.weg_safety.infra.persistence.jpa.EpiFuncionarioRepository;
import com.centro.weg_safety.infra.persistence.jpa.EpiTipoRepository;
import com.centro.weg_safety.infra.persistence.jpa.FuncionarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class VincularEpiUseCase {

    private final EpiFuncionarioRepository epiFuncionarioRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final EpiTipoRepository epiTipoRepository;

    public VincularEpiUseCase(EpiFuncionarioRepository epiFuncionarioRepository,
                               FuncionarioRepository funcionarioRepository,
                               EpiTipoRepository epiTipoRepository) {
        this.epiFuncionarioRepository = epiFuncionarioRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.epiTipoRepository = epiTipoRepository;
    }

    @Transactional
    public VincularEpiResponse execute(VincularEpiRequest request) {
        var funcionario = funcionarioRepository.findById(request.funcionarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado"));
        var epiTipo = epiTipoRepository.findById(request.epiTipoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tipo de EPI não encontrado"));

        StatusEpi status = calcularStatus(request.dataValidade());

        EpiFuncionario epi = EpiFuncionario.builder()
                .funcionario(funcionario)
                .epiTipo(epiTipo)
                .nrCa(request.nrCa())
                .dataEntrega(request.dataEntrega())
                .dataValidade(request.dataValidade())
                .status(status)
                .dataCriacao(LocalDateTime.now())
                .build();

        epi = epiFuncionarioRepository.save(epi);
        return new VincularEpiResponse(epi.getId(), "EPI vinculado com sucesso");
    }

    private StatusEpi calcularStatus(LocalDate validade) {
        LocalDate hoje = LocalDate.now();
        if (validade.isBefore(hoje)) return StatusEpi.VENCIDO;
        if (!validade.isAfter(hoje.plusDays(30))) return StatusEpi.VENCENDO;
        return StatusEpi.VALIDO;
    }
}
