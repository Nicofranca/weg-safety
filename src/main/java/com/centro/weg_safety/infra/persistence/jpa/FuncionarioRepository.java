package com.centro.weg_safety.infra.persistence.jpa;

import com.centro.weg_safety.domain.model.Funcionario;
import com.centro.weg_safety.domain.model.enums.funcionario.StatusFuncionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID> {
    Optional<Funcionario> findByCpf(String cpf);
    Optional<Funcionario> findByMatricula(String matricula);
    Optional<Funcionario> findByEmail(String email);
    List<Funcionario> findByStatus(StatusFuncionario status);
    List<Funcionario> findByCargoId(UUID cargoId);
    List<Funcionario> findBySetorId(UUID setorId);
}
