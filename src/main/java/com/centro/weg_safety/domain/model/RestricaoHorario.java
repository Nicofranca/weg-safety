package com.centro.weg_safety.domain.model;

import com.centro.weg_safety.domain.model.enums.restricaoHorario.DiaSemana;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "restricoes_horario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestricaoHorario {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", nullable = false)
    private Area area;

    @ElementCollection
    @CollectionTable(name = "restricao_dias", joinColumns = @JoinColumn(name = "restricao_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "dia")
    private List<DiaSemana> dias;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fim", nullable = false)
    private LocalTime horaFim;

    @Column(nullable = false)
    private String perfil;

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

}
