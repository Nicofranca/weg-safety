package com.centro.weg_safety.domain.model;

import com.centro.weg_safety.domain.model.enums.permissaoAcesso.TipoPermissao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "permissao_acesso")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PermissaoAcesso {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID areaId;

    private UUID funcionarioId;

    private TipoPermissao tipo;

    private String motivo;

    private LocalDateTime dataCriacao;

    private UUID criadoPor;

}
