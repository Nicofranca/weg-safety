package com.centro.weg_safety.infra.persistence.jpa;

import com.centro.weg_safety.domain.model.Area;
import com.centro.weg_safety.domain.model.enums.area.StatusArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AreaRepository extends JpaRepository<Area, UUID> {
    Optional<Area> findByCodigo(String codigo);
    List<Area> findByStatus(StatusArea status);
}
