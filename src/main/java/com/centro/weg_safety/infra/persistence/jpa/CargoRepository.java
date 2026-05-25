package com.centro.weg_safety.infra.persistence.jpa;

import com.centro.weg_safety.domain.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, UUID> {
}
