package com.centro.weg_safety.domain.repository;

import com.centro.weg_safety.domain.model.LogAcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LogAcessoRepository extends JpaRepository<LogAcesso, UUID> {
}
