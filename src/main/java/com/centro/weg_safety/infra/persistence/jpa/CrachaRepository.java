package com.centro.weg_safety.infra.persistence.jpa;

import com.centro.weg_safety.domain.model.Cracha;
import com.centro.weg_safety.domain.model.enums.cracha.StatusCracha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CrachaRepository extends JpaRepository<Cracha, UUID> {
    List<Cracha> findByFuncionarioId(UUID funcionarioId);
    Optional<Cracha> findByFuncionarioIdAndStatus(UUID funcionarioId, StatusCracha status);
    Optional<Cracha> findByUidRfid(String uidRfid);
}
