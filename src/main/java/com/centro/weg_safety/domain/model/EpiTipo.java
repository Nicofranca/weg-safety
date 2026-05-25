package com.centro.weg_safety.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "epi_tipos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EpiTipo {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String nome;

    private String descricao;

    private Boolean ativo;

}
