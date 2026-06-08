package com.centro.weg_safety.infra.persistence.jpa;

import com.centro.weg_safety.domain.model.LogAcesso;
import com.centro.weg_safety.domain.model.enums.logAcesso.ResultadoAcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface LogAcessoRepository extends JpaRepository<LogAcesso, UUID> {
    List<LogAcesso> findByAreaId(UUID areaId);
    List<LogAcesso> findByResultado(ResultadoAcesso resultado);
    List<LogAcesso> findByTsEventoBetween(LocalDateTime inicio, LocalDateTime fim);

    @Query("""
            SELECT l FROM LogAcesso l
            WHERE (:areaId IS NULL OR l.area.id = :areaId)
            AND (:resultado IS NULL OR l.resultado = :resultado)
            AND (:inicio IS NULL OR l.tsEvento >= :inicio)
            AND (:fim IS NULL OR l.tsEvento <= :fim)
            ORDER BY l.tsEvento DESC
            """)
    List<LogAcesso> findWithFilters(
            @Param("areaId") UUID areaId,
            @Param("resultado") ResultadoAcesso resultado,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );
}
