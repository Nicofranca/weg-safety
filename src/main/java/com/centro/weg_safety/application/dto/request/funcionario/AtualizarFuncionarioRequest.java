package com.centro.weg_safety.application.dto.request.funcionario;

import com.centro.weg_safety.domain.model.enums.funcionario.Genero;

import java.time.LocalDate;
import java.util.UUID;

public record AtualizarFuncionarioRequest(
        String nome,
        String cpf,
        String rg,
        String email,
        LocalDate dataNascimento,
        Genero genero,
        UUID cargoId,
        UUID setorId,
        LocalDate dataAdmissao,
        String turno
) {
}
