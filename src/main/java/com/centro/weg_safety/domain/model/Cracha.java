package com.centro.weg_safety.domain.model;

import com.centro.weg_safety.domain.model.enums.cracha.PerfilAcesso;
import com.centro.weg_safety.domain.model.enums.cracha.StatusCracha;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "crachas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cracha {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    @Column(name = "uid_rfid", unique = true, nullable = false)
    private String uidRfid;

    @Column(nullable = false)
    private LocalDate validade;

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil_acesso")
    private  PerfilAcesso perfilAcesso;

    @Enumerated(EnumType.STRING)
    private StatusCracha status;

    @Column(name = "data_emissao", updatable = false)
    private LocalDateTime dataEmissao;

    @Column(name = "emitido_por")
    private UUID emitidoPor;

}
