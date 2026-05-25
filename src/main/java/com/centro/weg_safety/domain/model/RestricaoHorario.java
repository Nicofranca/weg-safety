package com.centro.weg_safety.domain.model;

import com.centro.weg_safety.domain.model.enums.restricaoHorario.DiaSemana;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "restricao_horario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestricaoHorario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID areaId;

    private List<DiaSemana> dias;

    private LocalTime horaInicio;

    private LocalTime horaFim;

    private String perfil;

    private LocalDateTime dataCriacao;

}
