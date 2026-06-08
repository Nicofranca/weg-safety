package com.centro.weg_safety.infra.web.controller;

import com.centro.weg_safety.application.dto.response.dashboard.DashboardResumoResponse;
import com.centro.weg_safety.application.usecase.dashboard.DashboardResumoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@Tag(name = "Dashboard")
public class DashboardController {

    private final DashboardResumoUseCase dashboardResumoUseCase;

    public DashboardController(DashboardResumoUseCase dashboardResumoUseCase) {
        this.dashboardResumoUseCase = dashboardResumoUseCase;
    }

    @GetMapping
    @Operation(summary = "Resumo do dashboard")
    public ResponseEntity<DashboardResumoResponse> resumo() {
        return ResponseEntity.ok(dashboardResumoUseCase.execute());
    }
}
