package com.centro.weg_safety.infra.web.controller;

import com.centro.weg_safety.application.dto.request.cargo.NomeRequest;
import com.centro.weg_safety.application.dto.response.cargo.CargoResponse;
import com.centro.weg_safety.domain.exception.ConflitoException;
import com.centro.weg_safety.domain.exception.RecursoNaoEncontradoException;
import com.centro.weg_safety.domain.model.Cargo;
import com.centro.weg_safety.infra.persistence.jpa.CargoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cargos")
@Tag(name = "Cargos")
public class CargoController {

    private final CargoRepository cargoRepository;

    public CargoController(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    @GetMapping
    @Operation(summary = "Listar cargos")
    public ResponseEntity<List<CargoResponse>> listar() {
        return ResponseEntity.ok(cargoRepository.findAll().stream()
                .map(c -> new CargoResponse(c.getId(), c.getNome(), c.getAtivo()))
                .toList());
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Criar cargo")
    public ResponseEntity<CargoResponse> criar(@Valid @RequestBody NomeRequest request) {
        cargoRepository.findAll().stream()
                .filter(c -> c.getNome().equalsIgnoreCase(request.nome()))
                .findFirst()
                .ifPresent(c -> { throw new ConflitoException("Cargo já existe"); });

        Cargo cargo = Cargo.builder()
                .nome(request.nome())
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .build();
        cargo = cargoRepository.save(cargo);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CargoResponse(cargo.getId(), cargo.getNome(), cargo.getAtivo()));
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Atualizar cargo")
    public ResponseEntity<CargoResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody NomeRequest request) {
        var cargo = cargoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cargo não encontrado"));
        cargo.setNome(request.nome());
        cargo = cargoRepository.save(cargo);
        return ResponseEntity.ok(new CargoResponse(cargo.getId(), cargo.getNome(), cargo.getAtivo()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Desativar cargo (soft delete)")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        var cargo = cargoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cargo não encontrado"));
        cargo.setAtivo(false);
        cargoRepository.save(cargo);
        return ResponseEntity.noContent().build();
    }
}
