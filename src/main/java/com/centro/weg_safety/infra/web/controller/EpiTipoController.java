package com.centro.weg_safety.infra.web.controller;

import com.centro.weg_safety.application.dto.request.epi.AtualizarEpiTipoRequest;
import com.centro.weg_safety.application.dto.request.epi.CriarEpiTipoRequest;
import com.centro.weg_safety.application.dto.response.epi.EpiTipoResponse;
import com.centro.weg_safety.application.mapper.EpiMapper;
import com.centro.weg_safety.domain.exception.ConflitoException;
import com.centro.weg_safety.domain.exception.RecursoNaoEncontradoException;
import com.centro.weg_safety.domain.model.EpiTipo;
import com.centro.weg_safety.infra.persistence.jpa.EpiTipoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/epi-tipos")
@Tag(name = "Tipos de EPI")
public class EpiTipoController {

    private final EpiTipoRepository epiTipoRepository;
    private final EpiMapper epiMapper;

    public EpiTipoController(EpiTipoRepository epiTipoRepository, EpiMapper epiMapper) {
        this.epiTipoRepository = epiTipoRepository;
        this.epiMapper = epiMapper;
    }

    @GetMapping
    @Operation(summary = "Listar tipos de EPI")
    public ResponseEntity<List<EpiTipoResponse>> listar() {
        return ResponseEntity.ok(epiTipoRepository.findAll().stream()
                .map(epiMapper::toEpiTipoResponse).toList());
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Criar tipo de EPI")
    public ResponseEntity<EpiTipoResponse> criar(@Valid @RequestBody CriarEpiTipoRequest request) {
        epiTipoRepository.findAll().stream()
                .filter(e -> e.getNome().equalsIgnoreCase(request.nome()))
                .findFirst()
                .ifPresent(e -> { throw new ConflitoException("Tipo de EPI já existe"); });

        EpiTipo epiTipo = EpiTipo.builder()
                .nome(request.nome())
                .descricao(request.descricao())
                .ativo(true)
                .build();
        epiTipo = epiTipoRepository.save(epiTipo);
        return ResponseEntity.status(HttpStatus.CREATED).body(epiMapper.toEpiTipoResponse(epiTipo));
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Atualizar tipo de EPI")
    public ResponseEntity<EpiTipoResponse> atualizar(@PathVariable UUID id,
                                                     @Valid @RequestBody AtualizarEpiTipoRequest request) {
        var epiTipo = epiTipoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tipo de EPI não encontrado"));
        if (request.nome() != null) epiTipo.setNome(request.nome());
        if (request.descricao() != null) epiTipo.setDescricao(request.descricao());
        epiTipo = epiTipoRepository.save(epiTipo);
        return ResponseEntity.ok(epiMapper.toEpiTipoResponse(epiTipo));
    }
}
