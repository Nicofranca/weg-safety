package com.centro.weg_safety.infra.persistence.jpa;

import com.centro.weg_safety.domain.model.RestricaoHorario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RestricaoHorarioRepository extends JpaRepository<RestricaoHorario, UUID> {
    List<RestricaoHorario> findByAreaId(UUID areaId);
}
