package com.centro.weg_safety.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "epi_tipo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EpiTipo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    private String descricao;

    private Boolean ativo;

    private LocalDateTime dataCriacao;

}
