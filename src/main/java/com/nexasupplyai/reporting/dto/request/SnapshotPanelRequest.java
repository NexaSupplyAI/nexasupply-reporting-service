package com.nexasupplyai.reporting.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

public record SnapshotPanelRequest(
        @NotNull OffsetDateTime inicioPeriodo,
        @NotNull OffsetDateTime finPeriodo,
        @NotNull @DecimalMin("0") Long totalProductos,
        @NotNull @DecimalMin("0") Long productosStockBajo,
        @NotNull @DecimalMin("0") Long pedidosPendientes,
        @NotNull @DecimalMin("0") Long proveedoresActivos,
        @NotNull @DecimalMin("0") BigDecimal valorInventario,
        @NotNull @DecimalMin("0") BigDecimal valorPedidos,
        Map<String, Object> contenido,
        Map<String, Object> versionesOrigen
) {
}
