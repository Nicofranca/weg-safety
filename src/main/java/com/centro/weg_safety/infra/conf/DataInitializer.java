package com.centro.weg_safety.infra.conf;

import com.centro.weg_safety.application.dto.request.usuario.CriarUsuarioRequest;
import com.centro.weg_safety.application.usecase.usuario.CriarUsuarioUseCase;
import com.centro.weg_safety.infra.persistence.jpa.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initAdmin(UsuarioRepository usuarioRepository, CriarUsuarioUseCase criarUsuarioUseCase) {
        return args -> {
            if (usuarioRepository.findByEmail("admin@wegsafety.com").isEmpty()) {
                criarUsuarioUseCase.execute(new CriarUsuarioRequest(
                        "Administrador",
                        "admin@wegsafety.com",
                        "Admin@1234"
                ));
                System.out.println("Admin inicial criado: admin@wegsafety.com / Admin@1234");
            }
        };
    }
}
