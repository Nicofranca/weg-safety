package com.centro.weg_safety.application.dto.response.area;

import com.centro.weg_safety.application.dto.response.epi.EpiTipoResponse;
import com.centro.weg_safety.application.dto.response.horario.RestricaoHorarioResponse;
import com.centro.weg_safety.application.dto.response.permissao.PermissaoResponse;

import java.util.List;
import java.util.UUID;

public record AreaResponse(
        UUID id,
        String nome,
        String codigo,
        String tipo,
        String nivelRisco,
        Integer capacidadeMaxima,
        Boolean aintiPassback,
        Boolean verificarEpi,
        Boolean failOpen,
        String status,
        List<EpiTipoResponse> episObrigatorios,
        List<PermissaoResponse> whitelist,
        List<PermissaoResponse> blacklist,
        List<RestricaoHorarioResponse> restricoesHorarios
) {
}
