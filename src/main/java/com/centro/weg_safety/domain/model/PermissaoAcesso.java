package com.centro.weg_safety.domain.model;

import com.centro.weg_safety.domain.model.enums.permissaoAcesso.TipoPermissao;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "permissoes_acesso")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissaoAcesso {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", nullable = false)
    private  Area area;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPermissao tipo;

    @Column
    private String motivo;

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "criado_por")
    private UUID criadoPor;

}
