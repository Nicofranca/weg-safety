package com.centro.weg_safety.infra.web.controller;

import com.centro.weg_safety.application.dto.request.area.AlterarStatusAreaRequest;
import com.centro.weg_safety.application.dto.request.area.AtualizarAreaRequest;
import com.centro.weg_safety.application.dto.request.area.CriarAreaRequest;
import com.centro.weg_safety.application.dto.response.area.AreaResponse;
import com.centro.weg_safety.application.dto.response.area.AreaResumoResponse;
import com.centro.weg_safety.application.dto.response.area.CriarAreaResponse;
import com.centro.weg_safety.application.usecase.area.*;
import com.centro.weg_safety.domain.model.enums.area.StatusArea;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/areas")
@Tag(name = "Áreas")
public class AreaController {

    private final CriarAreaUseCase criarAreaUseCase;
    private final AtualizarAreaUseCase atualizarAreaUseCase;
    private final BuscarAreaUseCase buscarAreaUseCase;
    private final ListarAreasUseCase listarAreasUseCase;
    private final AlterarStatusAreaUseCase alterarStatusAreaUseCase;

    public AreaController(CriarAreaUseCase criarAreaUseCase,
                          AtualizarAreaUseCase atualizarAreaUseCase,
                          BuscarAreaUseCase buscarAreaUseCase,
                          ListarAreasUseCase listarAreasUseCase,
                          AlterarStatusAreaUseCase alterarStatusAreaUseCase) {
        this.criarAreaUseCase = criarAreaUseCase;
        this.atualizarAreaUseCase = atualizarAreaUseCase;
        this.buscarAreaUseCase = buscarAreaUseCase;
        this.listarAreasUseCase = listarAreasUseCase;
        this.alterarStatusAreaUseCase = alterarStatusAreaUseCase;
    }

    @GetMapping
    @Operation(summary = "Listar áreas")
    public ResponseEntity<List<AreaResumoResponse>> listar(@RequestParam(required = false) StatusArea status) {
        return ResponseEntity.ok(listarAreasUseCase.execute(status));
    }

    @PostMapping
    @Operation(summary = "Criar área")
    public ResponseEntity<CriarAreaResponse> criar(@Valid @RequestBody CriarAreaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(criarAreaUseCase.execute(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar área por ID")
    public ResponseEntity<AreaResponse> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(buscarAreaUseCase.execute(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar área")
    public ResponseEntity<AreaResponse> atualizar(@PathVariable UUID id,
                                                  @Valid @RequestBody AtualizarAreaRequest request) {
        return ResponseEntity.ok(atualizarAreaUseCase.execute(id, request));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Alterar status da área")
    public ResponseEntity<Void> alterarStatus(@PathVariable UUID id,
                                              @Valid @RequestBody AlterarStatusAreaRequest request) {
        alterarStatusAreaUseCase.execute(id, request);
        return ResponseEntity.noContent().build();
    }
}
