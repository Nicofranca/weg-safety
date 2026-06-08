package com.centro.weg_safety.application.mapper;

import com.centro.weg_safety.application.dto.response.log.LogAcessoDetailResponse;
import com.centro.weg_safety.application.dto.response.log.LogAcessoResponse;
import com.centro.weg_safety.domain.model.LogAcesso;
import org.springframework.stereotype.Component;

@Component
public class LogMapper {

    private final FuncionarioMapper funcionarioMapper;
    private final AreaMapper areaMapper;

    public LogMapper(FuncionarioMapper funcionarioMapper, AreaMapper areaMapper) {
        this.funcionarioMapper = funcionarioMapper;
        this.areaMapper = areaMapper;
    }

    public LogAcessoResponse toResponse(LogAcesso l) {
        return new LogAcessoResponse(
                l.getId(),
                l.getFuncionario() != null ? l.getFuncionario().getNome() : null,
                l.getFuncionario() != null ? l.getFuncionario().getMatricula() : null,
                l.getArea() != null ? l.getArea().getNome() : null,
                l.getResultado() != null ? l.getResultado().name() : null,
                l.getMotivo() != null ? l.getMotivo().name() : null,
                l.getTsEvento()
        );
    }

    public LogAcessoDetailResponse toDetailResponse(LogAcesso l) {
        return new LogAcessoDetailResponse(
                l.getId(),
                l.getFuncionario() != null ? funcionarioMapper.toResumoResponse(l.getFuncionario()) : null,
                l.getArea() != null ? areaMapper.toResumoResponse(l.getArea()) : null,
                l.getResultado() != null ? l.getResultado().name() : null,
                l.getMotivo() != null ? l.getMotivo().name() : null,
                l.getTsEvento()
        );
    }
}
