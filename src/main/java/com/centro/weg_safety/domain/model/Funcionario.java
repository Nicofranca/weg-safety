package com.centro.weg_safety.domain.model;

import com.centro.weg_safety.domain.model.enums.funcionario.Genero;
import com.centro.weg_safety.domain.model.enums.funcionario.StatusFuncionario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "funcionario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    private String cpf;

    private String rg;

    private String matricula;

    private String email;

    private LocalDate dataNascimento;

    private Genero genero;

    private UUID cargoId;

    private UUID setorId;

    private LocalDate dataAdmissao;

    private String turno;

    private StatusFuncionario status;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;

}
