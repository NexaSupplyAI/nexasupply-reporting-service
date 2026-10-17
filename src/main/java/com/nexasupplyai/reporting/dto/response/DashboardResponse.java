package com.nexasupplyai.reporting.dto.response;

import com.nexasupplyai.reporting.dto.external.PedidoResponse;
import com.nexasupplyai.reporting.dto.external.SaldoInventarioResponse;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record DashboardResponse(long totalProductos, long productosStockBajo, long pedidosPendientes,
        long proveedoresActivos, BigDecimal valorInventario, BigDecimal valorPedidos,
        List<SaldoInventarioResponse> stockBajo, List<PedidoResponse> pedidosPendientesDetalle,
        List<String> generadoDesde, OffsetDateTime generadoEn) { }
