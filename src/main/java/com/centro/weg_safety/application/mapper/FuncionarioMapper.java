package com.centro.weg_safety.application.mapper;

import com.centro.weg_safety.application.dto.response.cargo.CargoResponse;
import com.centro.weg_safety.application.dto.response.cracha.CrachaResumoResponse;
import com.centro.weg_safety.application.dto.response.funcionario.FuncionarioResponse;
import com.centro.weg_safety.application.dto.response.funcionario.FuncionarioResumoResponse;
import com.centro.weg_safety.application.dto.response.setor.SetorResponse;
import com.centro.weg_safety.domain.model.Cracha;
import com.centro.weg_safety.domain.model.Funcionario;
import com.centro.weg_safety.domain.model.enums.cracha.StatusCracha;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class FuncionarioMapper {

    private final EpiMapper epiMapper;

    public FuncionarioMapper(EpiMapper epiMapper) {
        this.epiMapper = epiMapper;
    }

    public FuncionarioResponse toResponse(Funcionario f) {
        Cracha crachaAtivo = f.getCrachas() == null ? null :
                f.getCrachas().stream()
                        .filter(c -> c.getStatus() == StatusCracha.ATIVO)
                        .findFirst()
                        .orElse(null);

        return new FuncionarioResponse(
                f.getId(),
                f.getNome(),
                f.getCpf(),
                f.getRg(),
                f.getMatricula(),
                f.getEmail(),
                f.getDataNascimento(),
                f.getGenero() != null ? f.getGenero().name() : null,
                f.getDataAdmissao(),
                f.getTurno(),
                f.getStatus() != null ? f.getStatus().name() : null,
                f.getCargo() != null ? new CargoResponse(f.getCargo().getId(), f.getCargo().getNome(), f.getCargo().getAtivo()) : null,
                f.getSetor() != null ? new SetorResponse(f.getSetor().getId(), f.getSetor().getNome(), f.getSetor().getAtivo()) : null,
                crachaAtivo != null ? epiMapper.toCrachaResponse(crachaAtivo, this) : null,
                f.getEpis() != null ? f.getEpis().stream().map(epiMapper::toEpiFuncionarioResponse).toList() : Collections.emptyList()
        );
    }

    public FuncionarioResumoResponse toResumoResponse(Funcionario f) {
        Cracha crachaAtivo = f.getCrachas() == null ? null :
                f.getCrachas().stream()
                        .filter(c -> c.getStatus() == StatusCracha.ATIVO)
                        .findFirst()
                        .orElse(null);

        CrachaResumoResponse crachaResumo = crachaAtivo != null
                ? new CrachaResumoResponse(crachaAtivo.getUidRfid(), crachaAtivo.getValidade(), crachaAtivo.getStatus().name())
                : null;

        return new FuncionarioResumoResponse(
                f.getId(),
                f.getNome(),
                f.getMatricula(),
                f.getCargo() != null ? f.getCargo().getNome() : null,
                f.getSetor() != null ? f.getSetor().getNome() : null,
                f.getStatus() != null ? f.getStatus().name() : null,
                crachaResumo
        );
    }
}
