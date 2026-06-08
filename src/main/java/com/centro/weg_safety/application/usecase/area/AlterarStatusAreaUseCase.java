package com.centro.weg_safety.application.usecase.area;

import com.centro.weg_safety.application.dto.request.area.AlterarStatusAreaRequest;
import com.centro.weg_safety.domain.exception.RecursoNaoEncontradoException;
import com.centro.weg_safety.infra.persistence.jpa.AreaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AlterarStatusAreaUseCase {

    private final AreaRepository areaRepository;

    public AlterarStatusAreaUseCase(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    @Transactional
    public void execute(UUID id, AlterarStatusAreaRequest request) {
        var area = areaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Área não encontrada"));
        area.setStatus(request.status());
        areaRepository.save(area);
    }
}
