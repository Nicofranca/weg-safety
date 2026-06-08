package com.centro.weg_safety.application.usecase.area;

import com.centro.weg_safety.application.dto.response.area.AreaResumoResponse;
import com.centro.weg_safety.application.mapper.AreaMapper;
import com.centro.weg_safety.domain.model.Area;
import com.centro.weg_safety.domain.model.enums.area.StatusArea;
import com.centro.weg_safety.infra.persistence.jpa.AreaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarAreasUseCase {

    private final AreaRepository areaRepository;
    private final AreaMapper areaMapper;

    public ListarAreasUseCase(AreaRepository areaRepository, AreaMapper areaMapper) {
        this.areaRepository = areaRepository;
        this.areaMapper = areaMapper;
    }

    public List<AreaResumoResponse> execute(StatusArea status) {
        List<Area> areas = status != null
                ? areaRepository.findByStatus(status)
                : areaRepository.findAll();

        return areas.stream().map(areaMapper::toResumoResponse).toList();
    }
}
