package com.centro.weg_safety.application.dto.response.dashboard;

public record DashboardResumoResponse(
        Integer totalFuncionariosAtivos,
        Integer totalAreasAtivas,
        Integer totalEpisVencidos,
        Integer totalEpisAVencer,
        Integer totalAcessosHoje,
        Integer totalNegadosHoje
) {
}
