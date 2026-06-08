package com.centro.weg_safety.application.mapper;

import com.centro.weg_safety.application.dto.response.permissao.PermissaoResponse;
import com.centro.weg_safety.domain.model.PermissaoAcesso;
import org.springframework.stereotype.Component;

@Component
public class PermissaoMapper {

    public PermissaoResponse toResponse(PermissaoAcesso p) {
        return new PermissaoResponse(
                p.getId(),
                p.getFuncionario() != null ? p.getFuncionario().getId() : null,
                p.getFuncionario() != null ? p.getFuncionario().getNome() : null,
                p.getFuncionario() != null ? p.getFuncionario().getMatricula() : null,
                p.getTipo() != null ? p.getTipo().name() : null,
                p.getMotivo()
        );
    }
}
