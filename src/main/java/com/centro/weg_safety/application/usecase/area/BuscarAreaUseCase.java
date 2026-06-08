package com.centro.weg_safety.application.usecase.area;

import com.centro.weg_safety.application.dto.response.area.AreaResponse;
import com.centro.weg_safety.application.mapper.AreaMapper;
import com.centro.weg_safety.domain.exception.RecursoNaoEncontradoException;
import com.centro.weg_safety.infra.persistence.jpa.AreaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BuscarAreaUseCase {

    private final AreaRepository areaRepository;
    private final AreaMapper areaMapper;

    public BuscarAreaUseCase(AreaRepository areaRepository, AreaMapper areaMapper) {
        this.areaRepository = areaRepository;
        this.areaMapper = areaMapper;
    }

    public AreaResponse execute(UUID id) {
        return areaRepository.findById(id)
                .map(areaMapper::toResponse)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Área não encontrada"));
    }
}
