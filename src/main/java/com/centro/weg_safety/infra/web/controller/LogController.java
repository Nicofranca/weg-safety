package com.centro.weg_safety.infra.web.controller;

import com.centro.weg_safety.application.dto.response.log.LogAcessoResponse;
import com.centro.weg_safety.application.usecase.log.ExportarLogsUseCase;
import com.centro.weg_safety.application.usecase.log.ListarLogsUseCase;
import com.centro.weg_safety.domain.model.enums.logAcesso.ResultadoAcesso;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/logs")
@Tag(name = "Logs de Acesso")
public class LogController {

    private final ListarLogsUseCase listarLogsUseCase;
    private final ExportarLogsUseCase exportarLogsUseCase;

    public LogController(ListarLogsUseCase listarLogsUseCase, ExportarLogsUseCase exportarLogsUseCase) {
        this.listarLogsUseCase = listarLogsUseCase;
        this.exportarLogsUseCase = exportarLogsUseCase;
    }

    @GetMapping
    @Operation(summary = "Listar logs de acesso com filtros")
    public ResponseEntity<Page<LogAcessoResponse>> listar(
            @RequestParam(required = false) UUID areaId,
            @RequestParam(required = false) ResultadoAcesso resultado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim,
            Pageable pageable) {
        return ResponseEntity.ok(listarLogsUseCase.execute(areaId, resultado, inicio, fim, pageable));
    }

    @GetMapping("/exportar")
    @Operation(summary = "Exportar logs de acesso em CSV")
    public ResponseEntity<byte[]> exportar(
            @RequestParam(required = false) UUID areaId,
            @RequestParam(required = false) ResultadoAcesso resultado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        String csv = exportarLogsUseCase.execute(areaId, resultado, inicio, fim);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=logs-acesso.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csv.getBytes());
    }
}
