package com.centro.weg_safety.infra.web.controller;

import com.centro.weg_safety.application.dto.request.epi.VincularEpiRequest;
import com.centro.weg_safety.application.dto.response.epi.EpiFuncionarioResponse;
import com.centro.weg_safety.application.dto.response.epi.EpiVencimentoResponse;
import com.centro.weg_safety.application.dto.response.epi.VincularEpiResponse;
import com.centro.weg_safety.application.usecase.epi.ListarEpisFuncionarioUseCase;
import com.centro.weg_safety.application.usecase.epi.ListarVencimentosUseCase;
import com.centro.weg_safety.application.usecase.epi.RemoverEpiUseCase;
import com.centro.weg_safety.application.usecase.epi.VincularEpiUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "EPIs")
public class EpiController {

    private final VincularEpiUseCase vincularEpiUseCase;
    private final RemoverEpiUseCase removerEpiUseCase;
    private final ListarEpisFuncionarioUseCase listarEpisFuncionarioUseCase;
    private final ListarVencimentosUseCase listarVencimentosUseCase;

    public EpiController(VincularEpiUseCase vincularEpiUseCase,
                         RemoverEpiUseCase removerEpiUseCase,
                         ListarEpisFuncionarioUseCase listarEpisFuncionarioUseCase,
                         ListarVencimentosUseCase listarVencimentosUseCase) {
        this.vincularEpiUseCase = vincularEpiUseCase;
        this.removerEpiUseCase = removerEpiUseCase;
        this.listarEpisFuncionarioUseCase = listarEpisFuncionarioUseCase;
        this.listarVencimentosUseCase = listarVencimentosUseCase;
    }

    @GetMapping("/funcionarios/{id}/epis")
    @Operation(summary = "Listar EPIs do funcionário")
    public ResponseEntity<List<EpiFuncionarioResponse>> listar(@PathVariable UUID id) {
        return ResponseEntity.ok(listarEpisFuncionarioUseCase.execute(id));
    }

    @PostMapping("/funcionarios/{id}/epis")
    @Operation(summary = "Vincular EPI ao funcionário")
    public ResponseEntity<VincularEpiResponse> vincular(@PathVariable UUID id,
                                                        @Valid @RequestBody VincularEpiRequest request) {
        var req = new VincularEpiRequest(id, request.epiTipoId(), request.nrCa(), request.dataEntrega(), request.dataValidade());
        return ResponseEntity.status(HttpStatus.CREATED).body(vincularEpiUseCase.execute(req));
    }

    @DeleteMapping("/epis/{id}")
    @Operation(summary = "Remover EPI")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        removerEpiUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/epis/vencimentos")
    @Operation(summary = "Listar EPIs vencidos e vencendo em 30 dias")
    public ResponseEntity<List<EpiVencimentoResponse>> vencimentos() {
        return ResponseEntity.ok(listarVencimentosUseCase.execute());
    }
}
