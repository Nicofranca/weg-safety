package com.centro.weg_safety.infra.web.controller;

import com.centro.weg_safety.application.dto.request.funcionario.AlterarStatusFuncionarioRequest;
import com.centro.weg_safety.application.dto.request.funcionario.AtualizarFuncionarioRequest;
import com.centro.weg_safety.application.dto.request.funcionario.CriarFuncionarioRequest;
import com.centro.weg_safety.application.dto.response.funcionario.CriarFuncionarioResponse;
import com.centro.weg_safety.application.dto.response.funcionario.FuncionarioResponse;
import com.centro.weg_safety.application.dto.response.funcionario.FuncionarioResumoResponse;
import com.centro.weg_safety.application.usecase.funcionario.*;
import com.centro.weg_safety.domain.model.enums.funcionario.StatusFuncionario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/funcionarios")
@Tag(name = "Funcionários")
public class FuncionarioController {

    private final CriarFuncionarioUseCase criarFuncionarioUseCase;
    private final AtualizarFuncionarioUseCase atualizarFuncionarioUseCase;
    private final BuscarFuncionarioUseCase buscarFuncionarioUseCase;
    private final ListarFuncionariosUseCase listarFuncionariosUseCase;
    private final AlterarStatusFuncionarioUseCase alterarStatusFuncionarioUseCase;

    public FuncionarioController(CriarFuncionarioUseCase criarFuncionarioUseCase,
                                 AtualizarFuncionarioUseCase atualizarFuncionarioUseCase,
                                 BuscarFuncionarioUseCase buscarFuncionarioUseCase,
                                 ListarFuncionariosUseCase listarFuncionariosUseCase,
                                 AlterarStatusFuncionarioUseCase alterarStatusFuncionarioUseCase) {
        this.criarFuncionarioUseCase = criarFuncionarioUseCase;
        this.atualizarFuncionarioUseCase = atualizarFuncionarioUseCase;
        this.buscarFuncionarioUseCase = buscarFuncionarioUseCase;
        this.listarFuncionariosUseCase = listarFuncionariosUseCase;
        this.alterarStatusFuncionarioUseCase = alterarStatusFuncionarioUseCase;
    }

    @GetMapping
    @Operation(summary = "Listar funcionários")
    public ResponseEntity<List<FuncionarioResumoResponse>> listar(
            @RequestParam(required = false) StatusFuncionario status,
            @RequestParam(required = false) UUID setorId,
            @RequestParam(required = false) UUID cargoId) {
        return ResponseEntity.ok(listarFuncionariosUseCase.execute(status, setorId, cargoId));
    }

    @PostMapping
    @Operation(summary = "Criar funcionário")
    public ResponseEntity<CriarFuncionarioResponse> criar(@Valid @RequestBody CriarFuncionarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(criarFuncionarioUseCase.execute(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar funcionário por ID")
    public ResponseEntity<FuncionarioResponse> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(buscarFuncionarioUseCase.execute(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar funcionário")
    public ResponseEntity<FuncionarioResponse> atualizar(@PathVariable UUID id,
                                                         @Valid @RequestBody AtualizarFuncionarioRequest request) {
        return ResponseEntity.ok(atualizarFuncionarioUseCase.execute(id, request));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Alterar status do funcionário")
    public ResponseEntity<Void> alterarStatus(@PathVariable UUID id,
                                              @Valid @RequestBody AlterarStatusFuncionarioRequest request) {
        alterarStatusFuncionarioUseCase.execute(id, request);
        return ResponseEntity.noContent().build();
    }
}
