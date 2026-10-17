package com.nexasupplyai.reporting.controller;

import com.nexasupplyai.reporting.dto.response.DashboardResponse;
import com.nexasupplyai.reporting.service.impl.DashboardAggregator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reportes")
@RequiredArgsConstructor
public class ReporteController {
    private final DashboardAggregator dashboardAggregator;

    @GetMapping("/dashboard")
    public DashboardResponse dashboard() {
        return dashboardAggregator.agregar();
    }
}
