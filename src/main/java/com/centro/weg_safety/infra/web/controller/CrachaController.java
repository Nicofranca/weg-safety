package com.centro.weg_safety.infra.web.controller;

import com.centro.weg_safety.application.dto.request.cracha.EmitirCrachaRequest;
import com.centro.weg_safety.application.dto.response.cracha.EmitirCrachaResponse;
import com.centro.weg_safety.application.usecase.cracha.EmitirCrachaUseCase;
import com.centro.weg_safety.application.usecase.cracha.InvalidarCrachaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Tag(name = "Crachás")
public class CrachaController {

    private final EmitirCrachaUseCase emitirCrachaUseCase;
    private final InvalidarCrachaUseCase invalidarCrachaUseCase;

    public CrachaController(EmitirCrachaUseCase emitirCrachaUseCase, InvalidarCrachaUseCase invalidarCrachaUseCase) {
        this.emitirCrachaUseCase = emitirCrachaUseCase;
        this.invalidarCrachaUseCase = invalidarCrachaUseCase;
    }

    @PostMapping("/funcionarios/{id}/crachas")
    @Operation(summary = "Emitir crachá para funcionário")
    public ResponseEntity<EmitirCrachaResponse> emitir(@PathVariable UUID id,
                                                       @Valid @RequestBody EmitirCrachaRequest request) {
        var req = new EmitirCrachaRequest(id, request.uidRfid(), request.validade(), request.perfilAcesso());
        return ResponseEntity.status(HttpStatus.CREATED).body(emitirCrachaUseCase.execute(req));
    }

    @DeleteMapping("/crachas/{id}")
    @Operation(summary = "Invalidar crachá")
    public ResponseEntity<Void> invalidar(@PathVariable UUID id) {
        invalidarCrachaUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
