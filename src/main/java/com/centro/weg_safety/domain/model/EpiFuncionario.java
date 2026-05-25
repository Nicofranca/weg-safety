package com.centro.weg_safety.domain.model;

import com.centro.weg_safety.domain.model.enums.epiFuncionario.StatusEpi;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "epi_funcionario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EpiFuncionario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID funcionarioId;

    private UUID epiTipoId;

    private String nrCa;

    private LocalDate dataEntrega;

    private LocalDate dataValidade;

    private StatusEpi status;

    private LocalDateTime dataCriacao;

    private UUID registradoPor;

}
