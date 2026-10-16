package com.nexasupplyai.reporting.service.interfaces;

import com.nexasupplyai.reporting.dto.request.ExportacionReporteRequest;
import com.nexasupplyai.reporting.dto.response.ExportacionReporteResponse;

import java.util.List;
import java.util.UUID;

public interface ExportacionReporteServiceInterface {

    List<ExportacionReporteResponse> listar();

    ExportacionReporteResponse obtenerPorId(UUID id);

    ExportacionReporteResponse crear(ExportacionReporteRequest request);

    ExportacionReporteResponse actualizar(
            UUID id,
            ExportacionReporteRequest request
    );

    void eliminar(UUID id);
}