package com.centro.weg_safety.application.dto.request.area;

import com.centro.weg_safety.application.dto.request.horario.RestricaoHorarioRequest;
import com.centro.weg_safety.application.dto.request.permissao.AdicionarPermissaoRequest;
import com.centro.weg_safety.domain.model.enums.area.NivelRisco;
import com.centro.weg_safety.domain.model.enums.area.TipoArea;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CriarAreaRequest(
        @NotBlank String nome,
        @NotBlank String codigo,
        @NotNull TipoArea tipo,
        @NotNull NivelRisco nivelRisco,
        Integer capacidadeMaxima,
        Boolean antiPassback,
        Boolean verificarEpi,
        Boolean failOpen,
        List<UUID> episObrigatorios,
        List<AdicionarPermissaoRequest> whitelist,
        List<AdicionarPermissaoRequest> blacklist,
        List<RestricaoHorarioRequest> restricoesHorario
) {
}
