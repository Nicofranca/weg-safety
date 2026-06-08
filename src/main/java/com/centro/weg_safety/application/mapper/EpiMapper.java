package com.centro.weg_safety.application.mapper;

import com.centro.weg_safety.application.dto.response.cracha.CrachaResponse;
import com.centro.weg_safety.application.dto.response.epi.EpiFuncionarioResponse;
import com.centro.weg_safety.application.dto.response.epi.EpiTipoResponse;
import com.centro.weg_safety.application.dto.response.epi.EpiVencimentoResponse;
import com.centro.weg_safety.domain.model.Cracha;
import com.centro.weg_safety.domain.model.EpiFuncionario;
import com.centro.weg_safety.domain.model.EpiTipo;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class EpiMapper {

    public EpiFuncionarioResponse toEpiFuncionarioResponse(EpiFuncionario epi) {
        int dias = (int) ChronoUnit.DAYS.between(LocalDate.now(), epi.getDataValidade());
        return new EpiFuncionarioResponse(
                epi.getId(),
                toEpiTipoResponse(epi.getEpiTipo()),
                epi.getNrCa(),
                epi.getDataEntrega(),
                epi.getDataValidade(),
                dias,
                epi.getStatus() != null ? epi.getStatus().name() : null
        );
    }

    public EpiVencimentoResponse toEpiVencimentoResponse(EpiFuncionario epi) {
        int dias = (int) ChronoUnit.DAYS.between(LocalDate.now(), epi.getDataValidade());
        return new EpiVencimentoResponse(
                epi.getId(),
                epi.getFuncionario().getId(),
                epi.getFuncionario().getNome(),
                epi.getFuncionario().getMatricula(),
                epi.getEpiTipo().getNome(),
                epi.getNrCa(),
                epi.getDataValidade(),
                dias,
                epi.getStatus() != null ? epi.getStatus().name() : null
        );
    }

    public EpiTipoResponse toEpiTipoResponse(EpiTipo epiTipo) {
        return new EpiTipoResponse(epiTipo.getId(), epiTipo.getNome(), epiTipo.getDescricao(), epiTipo.getAtivo());
    }

    public CrachaResponse toCrachaResponse(Cracha c, FuncionarioMapper funcionarioMapper) {
        return new CrachaResponse(
                c.getId(),
                c.getUidRfid(),
                c.getValidade(),
                c.getPerfilAcesso() != null ? c.getPerfilAcesso().name() : null,
                c.getStatus() != null ? c.getStatus().name() : null,
                c.getDataEmissao(),
                funcionarioMapper.toResumoResponse(c.getFuncionario())
        );
    }
}
