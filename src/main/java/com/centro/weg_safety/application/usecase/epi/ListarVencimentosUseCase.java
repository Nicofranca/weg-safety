package com.centro.weg_safety.application.usecase.epi;

import com.centro.weg_safety.application.dto.response.epi.EpiVencimentoResponse;
import com.centro.weg_safety.application.mapper.EpiMapper;
import com.centro.weg_safety.infra.persistence.jpa.EpiFuncionarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ListarVencimentosUseCase {

    private final EpiFuncionarioRepository epiFuncionarioRepository;
    private final EpiMapper epiMapper;

    public ListarVencimentosUseCase(EpiFuncionarioRepository epiFuncionarioRepository, EpiMapper epiMapper) {
        this.epiFuncionarioRepository = epiFuncionarioRepository;
        this.epiMapper = epiMapper;
    }

    public List<EpiVencimentoResponse> execute() {
        LocalDate hoje = LocalDate.now();
        LocalDate limite = hoje.plusDays(30);

        var vencidos = epiFuncionarioRepository.findByDataValidadeBefore(hoje);
        var vencendo = epiFuncionarioRepository.findByDataValidadeBetween(hoje, limite);

        return Stream.concat(vencidos.stream(), vencendo.stream())
                .distinct()
                .map(epiMapper::toEpiVencimentoResponse)
                .toList();
    }
}
