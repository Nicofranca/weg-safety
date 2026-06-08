package com.centro.weg_safety.application.usecase.dashboard;

import com.centro.weg_safety.application.dto.response.dashboard.DashboardResumoResponse;
import com.centro.weg_safety.domain.model.enums.area.StatusArea;
import com.centro.weg_safety.domain.model.enums.funcionario.StatusFuncionario;
import com.centro.weg_safety.domain.model.enums.logAcesso.ResultadoAcesso;
import com.centro.weg_safety.infra.persistence.jpa.AreaRepository;
import com.centro.weg_safety.infra.persistence.jpa.EpiFuncionarioRepository;
import com.centro.weg_safety.infra.persistence.jpa.FuncionarioRepository;
import com.centro.weg_safety.infra.persistence.jpa.LogAcessoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DashboardResumoUseCase {

    private final FuncionarioRepository funcionarioRepository;
    private final AreaRepository areaRepository;
    private final EpiFuncionarioRepository epiFuncionarioRepository;
    private final LogAcessoRepository logAcessoRepository;

    public DashboardResumoUseCase(FuncionarioRepository funcionarioRepository,
                                  AreaRepository areaRepository,
                                  EpiFuncionarioRepository epiFuncionarioRepository,
                                  LogAcessoRepository logAcessoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.areaRepository = areaRepository;
        this.epiFuncionarioRepository = epiFuncionarioRepository;
        this.logAcessoRepository = logAcessoRepository;
    }

    public DashboardResumoResponse execute() {
        int totalFuncionariosAtivos = funcionarioRepository.findByStatus(StatusFuncionario.ATIVO).size();
        int totalAreasAtivas = areaRepository.findByStatus(StatusArea.ATIVA).size();

        LocalDate hoje = LocalDate.now();
        int totalEpisVencidos = epiFuncionarioRepository.findByDataValidadeBefore(hoje).size();
        int totalEpisAVencer = epiFuncionarioRepository.findByDataValidadeBetween(hoje, hoje.plusDays(30)).size();

        LocalDateTime inicioHoje = hoje.atStartOfDay();
        LocalDateTime fimHoje = hoje.plusDays(1).atStartOfDay();
        var logsHoje = logAcessoRepository.findWithFilters(null, null, inicioHoje, fimHoje);
        int totalAcessosHoje = logsHoje.size();
        int totalNegadosHoje = (int) logsHoje.stream()
                .filter(l -> l.getResultado() == ResultadoAcesso.NEGADO)
                .count();

        return new DashboardResumoResponse(
                totalFuncionariosAtivos,
                totalAreasAtivas,
                totalEpisVencidos,
                totalEpisAVencer,
                totalAcessosHoje,
                totalNegadosHoje
        );
    }
}
