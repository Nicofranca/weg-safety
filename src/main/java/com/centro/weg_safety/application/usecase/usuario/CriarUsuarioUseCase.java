package com.centro.weg_safety.application.usecase.usuario;

import com.centro.weg_safety.application.dto.request.usuario.CriarUsuarioRequest;
import com.centro.weg_safety.application.dto.response.usuario.CriarUsuarioResponse;
import com.centro.weg_safety.domain.exception.ConflitoException;
import com.centro.weg_safety.domain.model.Usuario;
import com.centro.weg_safety.domain.model.enums.usuario.StatusUsuario;
import com.centro.weg_safety.infra.persistence.jpa.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CriarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public CriarUsuarioUseCase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public CriarUsuarioResponse execute(CriarUsuarioRequest request) {
        if (usuarioRepository.findByEmail(request.email()).isPresent()) {
            throw new ConflitoException("Email já cadastrado");
        }

        Usuario usuario = Usuario.builder()
                .nome(request.nome())
                .email(request.email())
                .senhaHash(passwordEncoder.encode(request.senha()))
                .status(StatusUsuario.ATIVO)
                .tentativasLogin(0)
                .dataCriacao(LocalDateTime.now())
                .build();

        usuario = usuarioRepository.save(usuario);
        return new CriarUsuarioResponse(usuario.getId(), "Usuário criado com sucesso");
    }
}
