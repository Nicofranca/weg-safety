package com.centro.weg_safety.domain.model;

import com.centro.weg_safety.domain.model.enums.usuario.StatusUsuario;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    private UUID id;

    private String nome;

    private String email ;

    private String senhaHash;

    StatusUsuario status;

    private Integer tentativasLogin;

    private LocalDateTime bloqueadoAte;

    private LocalDateTime ultimoAcesso;

    private LocalDateTime dataCriacao;

}
