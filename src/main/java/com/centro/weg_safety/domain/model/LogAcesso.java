package com.centro.weg_safety.domain.model;

import com.centro.weg_safety.domain.model.enums.logAcesso.MotivoNegacao;
import com.centro.weg_safety.domain.model.enums.logAcesso.ResultadoAcesso;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "log_acesso")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogAcesso {

    private UUID id;

    private UUID funcionarioId;

    private UUID areaId;

    private ResultadoAcesso resultado;

    private MotivoNegacao motivo;

    private LocalDateTime tsEvento;
}
