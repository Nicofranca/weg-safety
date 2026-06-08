package com.centro.weg_safety.application.usecase.log;

import com.centro.weg_safety.domain.model.LogAcesso;
import com.centro.weg_safety.domain.model.enums.logAcesso.ResultadoAcesso;
import com.centro.weg_safety.infra.persistence.jpa.LogAcessoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ExportarLogsUseCase {

    private final LogAcessoRepository logAcessoRepository;

    public ExportarLogsUseCase(LogAcessoRepository logAcessoRepository) {
        this.logAcessoRepository = logAcessoRepository;
    }

    public String execute(UUID areaId, ResultadoAcesso resultado, LocalDateTime inicio, LocalDateTime fim) {
        List<LogAcesso> logs = logAcessoRepository.findWithFilters(areaId, resultado, inicio, fim);

        StringBuilder csv = new StringBuilder();
        csv.append("funcionario,matricula,area,resultado,motivo,timestamp\n");

        for (LogAcesso log : logs) {
            csv.append(escapeCsv(log.getFuncionario() != null ? log.getFuncionario().getNome() : ""))
               .append(",")
               .append(escapeCsv(log.getFuncionario() != null ? log.getFuncionario().getMatricula() : ""))
               .append(",")
               .append(escapeCsv(log.getArea() != null ? log.getArea().getNome() : ""))
               .append(",")
               .append(log.getResultado() != null ? log.getResultado().name() : "")
               .append(",")
               .append(log.getMotivo() != null ? log.getMotivo().name() : "")
               .append(",")
               .append(log.getTsEvento())
               .append("\n");
        }

        return csv.toString();
    }

    private String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
