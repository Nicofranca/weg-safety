package com.centro.weg_safety.application.dto.response.funcionario;

import com.centro.weg_safety.application.dto.response.cargo.CargoResponse;
import com.centro.weg_safety.application.dto.response.cracha.CrachaResponse;
import com.centro.weg_safety.application.dto.response.epi.EpiFuncionarioResponse;
import com.centro.weg_safety.application.dto.response.setor.SetorResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record FuncionarioResponse(
        UUID id,
        String nome,
        String cpf,
        String rg,
        String matricula,
        String email,
        LocalDate dataNascimento,
        String genero,
        LocalDate dataAdmissao,
        String turno,
        String status,
        CargoResponse cargo,
        SetorResponse setor,
        CrachaResponse cracha,
        List<EpiFuncionarioResponse> epis
) {
}
