package com.centro.weg_safety.domain.model;

import com.centro.weg_safety.domain.model.enums.logAcesso.MotivoNegacao;
import com.centro.weg_safety.domain.model.enums.logAcesso.ResultadoAcesso;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "logs_acesso")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogAcesso {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Area area;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResultadoAcesso resultado;

    @Enumerated(EnumType.STRING)
    private MotivoNegacao motivo;

    @Column(name = "ts_evento", nullable = false, updatable = false)
    private LocalDateTime tsEvento;
}
