package com.centro.weg_safety.application.mapper;

import com.centro.weg_safety.application.dto.response.area.AreaResponse;
import com.centro.weg_safety.application.dto.response.area.AreaResumoResponse;
import com.centro.weg_safety.application.dto.response.epi.EpiTipoResponse;
import com.centro.weg_safety.application.dto.response.permissao.PermissaoResponse;
import com.centro.weg_safety.domain.model.Area;
import com.centro.weg_safety.domain.model.enums.permissaoAcesso.TipoPermissao;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class AreaMapper {

    private final HorarioMapper horarioMapper;
    private final PermissaoMapper permissaoMapper;
    private final EpiMapper epiMapper;

    public AreaMapper(HorarioMapper horarioMapper, PermissaoMapper permissaoMapper, EpiMapper epiMapper) {
        this.horarioMapper = horarioMapper;
        this.permissaoMapper = permissaoMapper;
        this.epiMapper = epiMapper;
    }

    public AreaResponse toResponse(Area a) {
        List<EpiTipoResponse> epis = a.getEpisObrigatorios() != null
                ? a.getEpisObrigatorios().stream().map(epiMapper::toEpiTipoResponse).toList()
                : Collections.emptyList();

        List<PermissaoResponse> whitelist = a.getPermissoes() != null
                ? a.getPermissoes().stream()
                        .filter(p -> p.getTipo() == TipoPermissao.WHITELIST)
                        .map(permissaoMapper::toResponse)
                        .toList()
                : Collections.emptyList();

        List<PermissaoResponse> blacklist = a.getPermissoes() != null
                ? a.getPermissoes().stream()
                        .filter(p -> p.getTipo() == TipoPermissao.BLACKLIST)
                        .map(permissaoMapper::toResponse)
                        .toList()
                : Collections.emptyList();

        return new AreaResponse(
                a.getId(),
                a.getNome(),
                a.getCodigo(),
                a.getTipo() != null ? a.getTipo().name() : null,
                a.getNivelRisco() != null ? a.getNivelRisco().name() : null,
                a.getCapacidadeMaxima(),
                a.getAntiPassback(),
                a.getVerificarEpi(),
                a.getFailOpen(),
                a.getStatus() != null ? a.getStatus().name() : null,
                epis,
                whitelist,
                blacklist,
                a.getRestricoesHorario() != null
                        ? a.getRestricoesHorario().stream().map(horarioMapper::toResponse).toList()
                        : Collections.emptyList()
        );
    }

    public AreaResumoResponse toResumoResponse(Area a) {
        int totalAutorizados = a.getPermissoes() != null
                ? (int) a.getPermissoes().stream().filter(p -> p.getTipo() == TipoPermissao.WHITELIST).count()
                : 0;

        List<String> nomesEpis = a.getEpisObrigatorios() != null
                ? a.getEpisObrigatorios().stream().map(e -> e.getNome()).toList()
                : Collections.emptyList();

        return new AreaResumoResponse(
                a.getId(),
                a.getNome(),
                a.getCodigo(),
                a.getTipo() != null ? a.getTipo().name() : null,
                a.getNivelRisco() != null ? a.getNivelRisco().name() : null,
                a.getStatus() != null ? a.getStatus().name() : null,
                totalAutorizados,
                nomesEpis
        );
    }
}
