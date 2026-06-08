package com.centro.weg_safety.application.usecase.auth;

import com.centro.weg_safety.application.dto.request.auth.LoginRequest;
import com.centro.weg_safety.application.dto.response.auth.LoginResponse;
import com.centro.weg_safety.domain.exception.DomainException;
import com.centro.weg_safety.domain.model.Usuario;
import com.centro.weg_safety.domain.model.enums.usuario.StatusUsuario;
import com.centro.weg_safety.infra.persistence.jpa.UsuarioRepository;
import com.centro.weg_safety.infra.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoginUseCase {

    private static final int MAX_TENTATIVAS = 5;
    private static final int MINUTOS_BLOQUEIO = 30;

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Value("${jwt.expiration}")
    private long expiration;

    public LoginUseCase(UsuarioRepository usuarioRepository,
                        PasswordEncoder passwordEncoder,
                        JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse execute(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new DomainException("Credenciais inválidas"));

        if (usuario.getStatus() == StatusUsuario.INATIVO) {
            throw new DomainException("Usuário inativo");
        }

        if (usuario.getBloqueadoAte() != null && usuario.getBloqueadoAte().isAfter(LocalDateTime.now())) {
            throw new DomainException("Usuário bloqueado até " + usuario.getBloqueadoAte());
        }

        if (!passwordEncoder.matches(request.senha(), usuario.getSenhaHash())) {
            int tentativas = usuario.getTentativasLogin() == null ? 0 : usuario.getTentativasLogin();
            tentativas++;
            usuario.setTentativasLogin(tentativas);

            if (tentativas >= MAX_TENTATIVAS) {
                usuario.setBloqueadoAte(LocalDateTime.now().plusMinutes(MINUTOS_BLOQUEIO));
                usuario.setTentativasLogin(0);
            }

            usuarioRepository.save(usuario);
            throw new DomainException("Credenciais inválidas");
        }

        usuario.setTentativasLogin(0);
        usuario.setBloqueadoAte(null);
        usuario.setUltimoAcesso(LocalDateTime.now());
        usuarioRepository.save(usuario);

        String token = jwtService.generateToken(usuario);
        LocalDateTime expiraEm = LocalDateTime.now().plusSeconds(expiration / 1000);

        return new LoginResponse(token, usuario.getNome(), usuario.getEmail(), expiraEm);
    }
}
