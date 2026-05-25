package com.centro.weg_safety.domain.model;

import com.centro.weg_safety.domain.model.enums.funcionario.Genero;
import com.centro.weg_safety.domain.model.enums.funcionario.StatusFuncionario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "funcionarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false, length = 11)
    private String cpf;

    @Column(length = 20)
    private String rg;

    @Column(unique = true, nullable = false, length = 20)
    private String matricula;

    @Column(unique = true)
    private String email;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setor_id")
    private Setor setor;

    @Column(name = "data_admissao")
    private LocalDate dataAdmissao;

    private String turno;

    @Enumerated(EnumType.STRING)
    private StatusFuncionario status;

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL)
    private List<Cracha> crachas;

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL)
    private List<EpiFuncionario> epis;
}
