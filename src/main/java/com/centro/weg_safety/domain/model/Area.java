package com.centro.weg_safety.domain.model;

import com.centro.weg_safety.domain.model.enums.area.NivelRisco;
import com.centro.weg_safety.domain.model.enums.area.StatusArea;
import com.centro.weg_safety.domain.model.enums.area.TipoArea;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "areas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Area {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false, length = 20)
    private String codigo;

    @Enumerated(EnumType.STRING)
    private TipoArea tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_risco")
    private NivelRisco nivelRisco;

    @Column(name = "capacidade_maxima")
    private Integer capacidadeMaxima;

    @Column(name = "anti_passback")
    private Boolean antiPassback;

    @Column(name = "verificar_epi")
    private Boolean verificarEpi;

    @Column(name = "fail_open")
    private Boolean failOpen;

    @Enumerated(EnumType.STRING)
    private StatusArea status;

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @ManyToMany
    @JoinTable(
            name = "epi_area",
            joinColumns = @JoinColumn(name = "area_id"),
            inverseJoinColumns = @JoinColumn(name = "epi_tipo_id")
    )
    private List<EpiTipo> episObrigatorios;

    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PermissaoAcesso> permissoes;

    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RestricaoHorario> restricoesHorario;

}
