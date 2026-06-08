package com.centro.weg_safety.infra.persistence.jpa;

import com.centro.weg_safety.domain.model.EpiFuncionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface EpiFuncionarioRepository extends JpaRepository<EpiFuncionario, UUID> {
    List<EpiFuncionario> findByFuncionarioId(UUID funcionarioId);
    List<EpiFuncionario> findByDataValidadeBefore(LocalDate data);
    List<EpiFuncionario> findByDataValidadeBetween(LocalDate inicio, LocalDate fim);
}
