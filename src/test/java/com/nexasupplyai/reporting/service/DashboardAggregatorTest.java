package com.nexasupplyai.reporting.service;

import com.nexasupplyai.reporting.client.*;
import com.nexasupplyai.reporting.service.impl.DashboardAggregator;
import com.nexasupplyai.reporting.dto.external.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardAggregatorTest {
    @Mock InventoryServiceClient inventario;
    @Mock OrderServiceClient pedidos;
    @Mock SupplierServiceClient proveedores;

    @Test
    void agregaMetricasDesdeLosTresDominios() {
        UUID productoId = UUID.randomUUID();
        ProductoResponse producto = new ProductoResponse(productoId, "SKU", null, "Producto", null, null,
                "UNIT", new BigDecimal("10"), null, null, null, true, null, null, null, 0L);
        SaldoInventarioResponse saldo = new SaldoInventarioResponse(UUID.randomUUID(), UUID.randomUUID(),
                productoId, new BigDecimal("3"), BigDecimal.ZERO, new BigDecimal("3"), null, 0L);
        PedidoResponse pedido = new PedidoResponse(UUID.randomUUID(), "ORD-1", null, null, null, null,
                "PENDING", "PEN", null, null, null, null, new BigDecimal("25"), null, null, null,
                null, null, null, null, null, 0L);
        ProveedorResponse proveedor = new ProveedorResponse(UUID.randomUUID(), "SUP-1", null, "Proveedor",
                null, null, null, "ACTIVE", null, null, null, null, null, null, null, null, null, 0L);
        when(inventario.listarProductos()).thenReturn(List.of(producto));
        when(inventario.listarSaldos()).thenReturn(List.of(saldo));
        when(inventario.listarStockBajo()).thenReturn(List.of(saldo));
        when(pedidos.listarPedidos()).thenReturn(List.of(pedido));
        when(proveedores.listarProveedores()).thenReturn(List.of(proveedor));

        var resultado = new DashboardAggregator(inventario, pedidos, proveedores).agregar();

        assertEquals(1, resultado.totalProductos());
        assertEquals(1, resultado.productosStockBajo());
        assertEquals(1, resultado.pedidosPendientes());
        assertEquals("30", resultado.valorInventario().toPlainString());
        assertEquals("25", resultado.valorPedidos().toPlainString());
    }
}
