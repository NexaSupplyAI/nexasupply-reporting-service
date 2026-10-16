package com.nexasupplyai.reporting.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

public record SnapshotPanelResponse(
        UUID id, OffsetDateTime inicioPeriodo, OffsetDateTime finPeriodo,
        Long totalProductos, Long productosStockBajo, Long pedidosPendientes,
        Long proveedoresActivos, BigDecimal valorInventario, BigDecimal valorPedidos,
        Map<String, Object> contenido, Map<String, Object> versionesOrigen,
        OffsetDateTime generadoEn
) {
}
