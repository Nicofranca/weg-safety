package com.centro.weg_safety.infra.web.controller;

import com.centro.weg_safety.application.dto.request.setor.NomeRequest;
import com.centro.weg_safety.application.dto.response.setor.SetorResponse;
import com.centro.weg_safety.domain.exception.ConflitoException;
import com.centro.weg_safety.domain.exception.RecursoNaoEncontradoException;
import com.centro.weg_safety.domain.model.Setor;
import com.centro.weg_safety.infra.persistence.jpa.SetorRepository;
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
@RequestMapping("/setores")
@Tag(name = "Setores")
public class SetorController {

    private final SetorRepository setorRepository;

    public SetorController(SetorRepository setorRepository) {
        this.setorRepository = setorRepository;
    }

    @GetMapping
    @Operation(summary = "Listar setores")
    public ResponseEntity<List<SetorResponse>> listar() {
        return ResponseEntity.ok(setorRepository.findAll().stream()
                .map(s -> new SetorResponse(s.getId(), s.getNome(), s.getAtivo()))
                .toList());
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Criar setor")
    public ResponseEntity<SetorResponse> criar(@Valid @RequestBody NomeRequest request) {
        setorRepository.findAll().stream()
                .filter(s -> s.getNome().equalsIgnoreCase(request.nome()))
                .findFirst()
                .ifPresent(s -> { throw new ConflitoException("Setor já existe"); });

        Setor setor = Setor.builder()
                .nome(request.nome())
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .build();
        setor = setorRepository.save(setor);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SetorResponse(setor.getId(), setor.getNome(), setor.getAtivo()));
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Atualizar setor")
    public ResponseEntity<SetorResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody NomeRequest request) {
        var setor = setorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Setor não encontrado"));
        setor.setNome(request.nome());
        setor = setorRepository.save(setor);
        return ResponseEntity.ok(new SetorResponse(setor.getId(), setor.getNome(), setor.getAtivo()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Desativar setor (soft delete)")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        var setor = setorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Setor não encontrado"));
        setor.setAtivo(false);
        setorRepository.save(setor);
        return ResponseEntity.noContent().build();
    }
}
