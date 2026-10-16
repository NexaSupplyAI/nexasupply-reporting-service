package com.nexasupplyai.reporting.controller;

import com.nexasupplyai.reporting.dto.request.SnapshotPanelRequest;
import com.nexasupplyai.reporting.dto.response.SnapshotPanelResponse;
import com.nexasupplyai.reporting.service.interfaces.SnapshotPanelServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/snapshots-panel")
@RequiredArgsConstructor
public class SnapshotPanelController {

    private final SnapshotPanelServiceInterface servicio;

    @GetMapping
    public List<SnapshotPanelResponse> listar(
            @RequestParam(required = false) OffsetDateTime inicio,
            @RequestParam(required = false) OffsetDateTime fin
    ) {
        return inicio != null && fin != null ? servicio.listarPorPeriodo(inicio, fin) : servicio.listar();
    }

    @GetMapping("/{id}")
    public SnapshotPanelResponse obtener(@PathVariable UUID id) {
        return servicio.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<SnapshotPanelResponse> crear(@Valid @RequestBody SnapshotPanelRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicio.crear(request));
    }

    @PutMapping("/{id}")
    public SnapshotPanelResponse actualizar(@PathVariable UUID id, @Valid @RequestBody SnapshotPanelRequest request) {
        return servicio.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
