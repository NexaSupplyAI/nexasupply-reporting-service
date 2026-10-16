package com.nexasupplyai.reporting.service.interfaces;

import com.nexasupplyai.reporting.dto.request.SnapshotPanelRequest;
import com.nexasupplyai.reporting.dto.response.SnapshotPanelResponse;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface SnapshotPanelServiceInterface {

    List<SnapshotPanelResponse> listar();

    List<SnapshotPanelResponse> listarPorPeriodo(
            OffsetDateTime inicio,
            OffsetDateTime fin
    );

    SnapshotPanelResponse obtenerPorId(UUID id);

    SnapshotPanelResponse crear(SnapshotPanelRequest request);

    SnapshotPanelResponse actualizar(
            UUID id,
            SnapshotPanelRequest request
    );

    void eliminar(UUID id);
}