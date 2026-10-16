package com.nexasupplyai.reporting.dto.response;

import com.nexasupplyai.reporting.enums.EstadoExportacionReporte;
import com.nexasupplyai.reporting.enums.FormatoSalidaReporte;
import com.nexasupplyai.reporting.enums.TipoReporte;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

public record ExportacionReporteResponse(
        UUID id, TipoReporte tipoReporte, FormatoSalidaReporte formatoSalida,
        EstadoExportacionReporte estado, String uriAlmacenamiento,
        Map<String, Object> filtros, String solicitadoPor, String mensajeError,
        OffsetDateTime solicitadoEn, OffsetDateTime generadoEn, OffsetDateTime expiraEn
) {
}
