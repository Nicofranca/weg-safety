package com.centro.weg_safety.application.usecase.permissao;

import com.centro.weg_safety.application.dto.request.permissao.AdicionarPermissaoRequest;
import com.centro.weg_safety.application.dto.response.permissao.PermissaoResponse;
import com.centro.weg_safety.application.mapper.PermissaoMapper;
import com.centro.weg_safety.domain.exception.ConflitoException;
import com.centro.weg_safety.domain.exception.DomainException;
import com.centro.weg_safety.domain.exception.RecursoNaoEncontradoException;
import com.centro.weg_safety.domain.model.PermissaoAcesso;
import com.centro.weg_safety.domain.model.enums.permissaoAcesso.TipoPermissao;
import com.centro.weg_safety.infra.persistence.jpa.AreaRepository;
import com.centro.weg_safety.infra.persistence.jpa.FuncionarioRepository;
import com.centro.weg_safety.infra.persistence.jpa.PermissaoAcessoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AdicionarPermissaoUseCase {

    private final PermissaoAcessoRepository permissaoRepository;
    private final AreaRepository areaRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final PermissaoMapper permissaoMapper;

    public AdicionarPermissaoUseCase(PermissaoAcessoRepository permissaoRepository,
                                     AreaRepository areaRepository,
                                     FuncionarioRepository funcionarioRepository,
                                     PermissaoMapper permissaoMapper) {
        this.permissaoRepository = permissaoRepository;
        this.areaRepository = areaRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.permissaoMapper = permissaoMapper;
    }

    @Transactional
    public PermissaoResponse execute(UUID areaId, AdicionarPermissaoRequest request) {
        var area = areaRepository.findById(areaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Área não encontrada"));
        var funcionario = funcionarioRepository.findById(request.funcionarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado"));

        permissaoRepository.findByAreaIdAndFuncionarioIdAndTipo(areaId, request.funcionarioId(), request.tipo())
                .ifPresent(p -> { throw new ConflitoException("Permissão já existe para este funcionário nesta área"); });

        if (request.tipo() == TipoPermissao.BLACKLIST &&
                (request.motivo() == null || request.motivo().isBlank())) {
            throw new DomainException("Motivo é obrigatório para blacklist");
        }

        PermissaoAcesso permissao = PermissaoAcesso.builder()
                .area(area)
                .funcionario(funcionario)
                .tipo(request.tipo())
                .motivo(request.motivo())
                .dataCriacao(LocalDateTime.now())
                .build();

        permissao = permissaoRepository.save(permissao);
        return permissaoMapper.toResponse(permissao);
    }
}
