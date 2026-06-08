package com.centro.weg_safety.infra.web.controller;

import com.centro.weg_safety.application.dto.request.horario.RestricaoHorarioRequest;
import com.centro.weg_safety.application.dto.response.horario.RestricaoHorarioResponse;
import com.centro.weg_safety.application.mapper.HorarioMapper;
import com.centro.weg_safety.application.usecase.horario.AtualizarRestricaoHorarioUseCase;
import com.centro.weg_safety.application.usecase.horario.CriarRestricaoHorarioUseCase;
import com.centro.weg_safety.application.usecase.horario.RemoverRestricaoHorarioUseCase;
import com.centro.weg_safety.infra.persistence.jpa.RestricaoHorarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Tag(name = "Restrições de Horário")
public class HorarioController {

    private final CriarRestricaoHorarioUseCase criarRestricaoHorarioUseCase;
    private final AtualizarRestricaoHorarioUseCase atualizarRestricaoHorarioUseCase;
    private final RemoverRestricaoHorarioUseCase removerRestricaoHorarioUseCase;
    private final RestricaoHorarioRepository restricaoHorarioRepository;
    private final HorarioMapper horarioMapper;

    public HorarioController(CriarRestricaoHorarioUseCase criarRestricaoHorarioUseCase,
                             AtualizarRestricaoHorarioUseCase atualizarRestricaoHorarioUseCase,
                             RemoverRestricaoHorarioUseCase removerRestricaoHorarioUseCase,
                             RestricaoHorarioRepository restricaoHorarioRepository,
                             HorarioMapper horarioMapper) {
        this.criarRestricaoHorarioUseCase = criarRestricaoHorarioUseCase;
        this.atualizarRestricaoHorarioUseCase = atualizarRestricaoHorarioUseCase;
        this.removerRestricaoHorarioUseCase = removerRestricaoHorarioUseCase;
        this.restricaoHorarioRepository = restricaoHorarioRepository;
        this.horarioMapper = horarioMapper;
    }

    @GetMapping("/areas/{id}/horarios")
    @Operation(summary = "Listar restrições de horário da área")
    public ResponseEntity<List<RestricaoHorarioResponse>> listar(@PathVariable UUID id) {
        return ResponseEntity.ok(restricaoHorarioRepository.findByAreaId(id)
                .stream().map(horarioMapper::toResponse).toList());
    }

    @PostMapping("/areas/{id}/horarios")
    @Operation(summary = "Criar restrição de horário")
    public ResponseEntity<RestricaoHorarioResponse> criar(@PathVariable UUID id,
                                                          @Valid @RequestBody RestricaoHorarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(criarRestricaoHorarioUseCase.execute(id, request));
    }

    @PutMapping("/horarios/{id}")
    @Operation(summary = "Atualizar restrição de horário")
    public ResponseEntity<RestricaoHorarioResponse> atualizar(@PathVariable UUID id,
                                                              @Valid @RequestBody RestricaoHorarioRequest request) {
        return ResponseEntity.ok(atualizarRestricaoHorarioUseCase.execute(id, request));
    }

    @DeleteMapping("/horarios/{id}")
    @Operation(summary = "Remover restrição de horário")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        removerRestricaoHorarioUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
