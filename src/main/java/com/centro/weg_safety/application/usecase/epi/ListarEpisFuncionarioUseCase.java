package com.centro.weg_safety.application.usecase.epi;

import com.centro.weg_safety.application.dto.response.epi.EpiFuncionarioResponse;
import com.centro.weg_safety.application.mapper.EpiMapper;
import com.centro.weg_safety.infra.persistence.jpa.EpiFuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListarEpisFuncionarioUseCase {

    private final EpiFuncionarioRepository epiFuncionarioRepository;
    private final EpiMapper epiMapper;

    public ListarEpisFuncionarioUseCase(EpiFuncionarioRepository epiFuncionarioRepository, EpiMapper epiMapper) {
        this.epiFuncionarioRepository = epiFuncionarioRepository;
        this.epiMapper = epiMapper;
    }

    public List<EpiFuncionarioResponse> execute(UUID funcionarioId) {
        return epiFuncionarioRepository.findByFuncionarioId(funcionarioId)
                .stream()
                .map(epiMapper::toEpiFuncionarioResponse)
                .toList();
    }
}
