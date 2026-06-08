package com.centro.weg_safety.application.usecase.area;

import com.centro.weg_safety.application.dto.request.area.CriarAreaRequest;
import com.centro.weg_safety.application.dto.response.area.CriarAreaResponse;
import com.centro.weg_safety.domain.exception.ConflitoException;
import com.centro.weg_safety.domain.model.Area;
import com.centro.weg_safety.domain.model.EpiTipo;
import com.centro.weg_safety.domain.model.enums.area.StatusArea;
import com.centro.weg_safety.infra.persistence.jpa.AreaRepository;
import com.centro.weg_safety.infra.persistence.jpa.EpiTipoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class CriarAreaUseCase {

    private final AreaRepository areaRepository;
    private final EpiTipoRepository epiTipoRepository;

    public CriarAreaUseCase(AreaRepository areaRepository, EpiTipoRepository epiTipoRepository) {
        this.areaRepository = areaRepository;
        this.epiTipoRepository = epiTipoRepository;
    }

    @Transactional
    public CriarAreaResponse execute(CriarAreaRequest request) {
        if (areaRepository.findByCodigo(request.codigo()).isPresent()) {
            throw new ConflitoException("Código de área já cadastrado");
        }

        List<EpiTipo> episObrigatorios = Collections.emptyList();
        if (request.episObrigatorios() != null && !request.episObrigatorios().isEmpty()) {
            episObrigatorios = epiTipoRepository.findAllById(request.episObrigatorios());
        }

        Area area = Area.builder()
                .nome(request.nome())
                .codigo(request.codigo())
                .tipo(request.tipo())
                .nivelRisco(request.nivelRisco())
                .capacidadeMaxima(request.capacidadeMaxima())
                .antiPassback(request.antiPassback() != null ? request.antiPassback() : false)
                .verificarEpi(request.verificarEpi() != null ? request.verificarEpi() : false)
                .failOpen(request.failOpen() != null ? request.failOpen() : false)
                .status(StatusArea.ATIVA)
                .dataCriacao(LocalDateTime.now())
                .episObrigatorios(episObrigatorios)
                .build();

        area = areaRepository.save(area);
        return new CriarAreaResponse(area.getId(), "Área criada com sucesso");
    }
}
