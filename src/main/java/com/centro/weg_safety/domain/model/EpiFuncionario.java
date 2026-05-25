package com.centro.weg_safety.domain.model;

import com.centro.weg_safety.domain.model.enums.epiFuncionario.StatusEpi;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "epi_funcionarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EpiFuncionario {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "epi_tipo_id", nullable = false)
    private EpiTipo epiTipo;

    @Column(name = "nr_ca", nullable = false)
    private String nrCa;

    @Column(name = "data_entrega")
    private LocalDate dataEntrega;

    @Column(name = "data_validade", nullable = false)
    private LocalDate dataValidade;

    @Enumerated(EnumType.STRING)
    private StatusEpi status;

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "registrado_por")
    private UUID registradoPor;

}
