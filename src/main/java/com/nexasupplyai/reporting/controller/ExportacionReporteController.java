package com.nexasupplyai.reporting.controller;

import com.nexasupplyai.reporting.dto.request.ExportacionReporteRequest;
import com.nexasupplyai.reporting.dto.response.ExportacionReporteResponse;
import com.nexasupplyai.reporting.service.interfaces.ExportacionReporteServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/exportaciones-reportes")
@RequiredArgsConstructor
public class ExportacionReporteController {

    private final ExportacionReporteServiceInterface servicio;

    @GetMapping
    public List<ExportacionReporteResponse> listar() {
        return servicio.listar();
    }

    @GetMapping("/{id}")
    public ExportacionReporteResponse obtener(@PathVariable UUID id) {
        return servicio.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<ExportacionReporteResponse> crear(@Valid @RequestBody ExportacionReporteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicio.crear(request));
    }

    @PutMapping("/{id}")
    public ExportacionReporteResponse actualizar(@PathVariable UUID id, @Valid @RequestBody ExportacionReporteRequest request) {
        return servicio.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
