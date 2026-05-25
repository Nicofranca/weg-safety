package com.centro.weg_safety.domain.model;

import com.centro.weg_safety.domain.model.enums.area.NivelRisco;
import com.centro.weg_safety.domain.model.enums.area.StatusArea;
import com.centro.weg_safety.domain.model.enums.area.TipoArea;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "area")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    private String codigo;

    private TipoArea tipo;

    private NivelRisco nivelRisco ;

    private Integer capacidadeMaxima;

    private Boolean antiPassback;

    private Boolean verificarEpi;

    private Boolean failOpen;

    private StatusArea status;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;



}
