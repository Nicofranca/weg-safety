package com.centro.weg_safety.application.dto.request.funcionario;

import com.centro.weg_safety.application.dto.request.cracha.EmitirCrachaRequest;
import com.centro.weg_safety.application.dto.request.epi.VincularEpiRequest;
import com.centro.weg_safety.domain.model.enums.funcionario.Genero;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CriarFuncionarioRequest(
        @NotBlank String nome,
        @NotBlank String cpf,
        String rg,
        @Email String email,
        LocalDate dataNascimento,
        Genero genero,
        @NotNull UUID cargoId,
        @NotNull UUID setorId,
        LocalDate dataAdmissao,
        String turno,
        @NotNull EmitirCrachaRequest cracha,
        List<VincularEpiRequest> epis
        ) {
}
