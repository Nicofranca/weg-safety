package com.centro.weg_safety.application.usecase.log;

import com.centro.weg_safety.application.dto.response.log.LogAcessoResponse;
import com.centro.weg_safety.application.mapper.LogMapper;
import com.centro.weg_safety.domain.model.enums.logAcesso.ResultadoAcesso;
import com.centro.weg_safety.infra.persistence.jpa.LogAcessoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ListarLogsUseCase {

    private final LogAcessoRepository logAcessoRepository;
    private final LogMapper logMapper;

    public ListarLogsUseCase(LogAcessoRepository logAcessoRepository, LogMapper logMapper) {
        this.logAcessoRepository = logAcessoRepository;
        this.logMapper = logMapper;
    }

    public Page<LogAcessoResponse> execute(UUID areaId, ResultadoAcesso resultado,
                                           LocalDateTime inicio, LocalDateTime fim,
                                           Pageable pageable) {
        var logs = logAcessoRepository.findWithFilters(areaId, resultado, inicio, fim);
        var responses = logs.stream().map(logMapper::toResponse).toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), responses.size());
        var page = start > responses.size() ? responses.subList(0, 0) : responses.subList(start, end);

        return new PageImpl<>(page, pageable, responses.size());
    }
}
