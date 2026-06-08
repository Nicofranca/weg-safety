package com.centro.weg_safety.infra.web.controller;

import com.centro.weg_safety.application.dto.request.permissao.AdicionarPermissaoRequest;
import com.centro.weg_safety.application.dto.response.permissao.PermissaoResponse;
import com.centro.weg_safety.application.dto.response.permissao.PermissoesResponse;
import com.centro.weg_safety.application.mapper.PermissaoMapper;
import com.centro.weg_safety.application.usecase.permissao.AdicionarPermissaoUseCase;
import com.centro.weg_safety.application.usecase.permissao.RemoverPermissaoUseCase;
import com.centro.weg_safety.domain.model.enums.permissaoAcesso.TipoPermissao;
import com.centro.weg_safety.infra.persistence.jpa.PermissaoAcessoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Permissões de Acesso")
public class PermissaoController {

    private final AdicionarPermissaoUseCase adicionarPermissaoUseCase;
    private final RemoverPermissaoUseCase removerPermissaoUseCase;
    private final PermissaoAcessoRepository permissaoRepository;
    private final PermissaoMapper permissaoMapper;

    public PermissaoController(AdicionarPermissaoUseCase adicionarPermissaoUseCase,
                               RemoverPermissaoUseCase removerPermissaoUseCase,
                               PermissaoAcessoRepository permissaoRepository,
                               PermissaoMapper permissaoMapper) {
        this.adicionarPermissaoUseCase = adicionarPermissaoUseCase;
        this.removerPermissaoUseCase = removerPermissaoUseCase;
        this.permissaoRepository = permissaoRepository;
        this.permissaoMapper = permissaoMapper;
    }

    @GetMapping("/areas/{id}/permissoes")
    @Operation(summary = "Listar permissões da área")
    public ResponseEntity<PermissoesResponse> listar(@PathVariable UUID id) {
        var todas = permissaoRepository.findByAreaId(id);
        List<PermissaoResponse> whitelist = todas.stream()
                .filter(p -> p.getTipo() == TipoPermissao.WHITELIST)
                .map(permissaoMapper::toResponse)
                .toList();
        List<PermissaoResponse> blacklist = todas.stream()
                .filter(p -> p.getTipo() == TipoPermissao.BLACKLIST)
                .map(permissaoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(new PermissoesResponse(whitelist, blacklist));
    }

    @PostMapping("/areas/{id}/permissoes")
    @Operation(summary = "Adicionar permissão à área")
    public ResponseEntity<PermissaoResponse> adicionar(@PathVariable UUID id,
                                                       @Valid @RequestBody AdicionarPermissaoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adicionarPermissaoUseCase.execute(id, request));
    }

    @DeleteMapping("/permissoes/{id}")
    @Operation(summary = "Remover permissão")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        removerPermissaoUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
