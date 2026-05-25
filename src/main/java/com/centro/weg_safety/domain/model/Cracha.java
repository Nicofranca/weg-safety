package com.centro.weg_safety.domain.model;

import com.centro.weg_safety.domain.model.enums.cracha.PerfilAcesso;
import com.centro.weg_safety.domain.model.enums.cracha.StatusCracha;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "cracha")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cracha {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID funcionarioId;

    private String uidRfid;

    private LocalDate validade;

    private PerfilAcesso perfilAcesso;

    private StatusCracha status;

    private LocalDateTime dataEmissao;

    private UUID emitidoPor;

}
