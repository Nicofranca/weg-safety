package com.centro.weg_safety.application.usecase.area;

import com.centro.weg_safety.application.dto.request.area.AtualizarAreaRequest;
import com.centro.weg_safety.application.dto.response.area.AreaResponse;
import com.centro.weg_safety.application.mapper.AreaMapper;
import com.centro.weg_safety.domain.exception.RecursoNaoEncontradoException;
import com.centro.weg_safety.domain.model.Area;
import com.centro.weg_safety.infra.persistence.jpa.AreaRepository;
import com.centro.weg_safety.infra.persistence.jpa.EpiTipoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.UUID;

@Service
public class AtualizarAreaUseCase {

    private final AreaRepository areaRepository;
    private final EpiTipoRepository epiTipoRepository;
    private final AreaMapper areaMapper;

    public AtualizarAreaUseCase(AreaRepository areaRepository, EpiTipoRepository epiTipoRepository, AreaMapper areaMapper) {
        this.areaRepository = areaRepository;
        this.epiTipoRepository = epiTipoRepository;
        this.areaMapper = areaMapper;
    }

    @Transactional
    public AreaResponse execute(UUID id, AtualizarAreaRequest request) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Área não encontrada"));

        if (request.nome() != null) area.setNome(request.nome());
        if (request.codigo() != null) area.setCodigo(request.codigo());
        if (request.tipo() != null) area.setTipo(request.tipo());
        if (request.nivelRisco() != null) area.setNivelRisco(request.nivelRisco());
        if (request.capacidadeMaxima() != null) area.setCapacidadeMaxima(request.capacidadeMaxima());
        if (request.antiPassback() != null) area.setAntiPassback(request.antiPassback());
        if (request.verificarEpi() != null) area.setVerificarEpi(request.verificarEpi());
        if (request.failOpen() != null) area.setFailOpen(request.failOpen());

        if (request.episObrigatorios() != null) {
            area.setEpisObrigatorios(epiTipoRepository.findAllById(request.episObrigatorios()));
        }

        area = areaRepository.save(area);
        return areaMapper.toResponse(area);
    }
}
