package com.nexasupplyai.reporting.dto.request;

import com.nexasupplyai.reporting.enums.FormatoSalidaReporte;
import com.nexasupplyai.reporting.enums.TipoReporte;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.Map;

public record ExportacionReporteRequest(
        @NotNull TipoReporte tipoReporte,
        @NotNull FormatoSalidaReporte formatoSalida,
        Map<String, Object> filtros,
        @Size(max = 120) String solicitadoPor,
        OffsetDateTime expiraEn
) {
}
