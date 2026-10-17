package com.nexasupplyai.reporting.service;

import com.nexasupplyai.reporting.client.*;
import com.nexasupplyai.reporting.service.impl.DashboardAggregator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardAggregatorTest {
    @Mock InventoryServiceClient inventario;
    @Mock OrderServiceClient pedidos;
    @Mock SupplierServiceClient proveedores;

    @Test
    void agregaMetricasDesdeLosTresDominios() {
        when(inventario.listarProductos()).thenReturn(List.of(Map.of("id", "p1", "precioVenta", 10)));
        when(inventario.listarSaldos()).thenReturn(List.of(Map.of("idProducto", "p1", "cantidadEnMano", 3)));
        when(inventario.listarStockBajo()).thenReturn(List.of(Map.of("idProducto", "p1")));
        when(pedidos.listarPedidos()).thenReturn(List.of(Map.of("estado", "PENDING", "montoTotal", 25)));
        when(proveedores.listarProveedores()).thenReturn(List.of(Map.of("estado", "ACTIVE")));

        var resultado = new DashboardAggregator(inventario, pedidos, proveedores).agregar();

        assertEquals(1, resultado.totalProductos());
        assertEquals(1, resultado.productosStockBajo());
        assertEquals(1, resultado.pedidosPendientes());
        assertEquals("30", resultado.valorInventario().toPlainString());
        assertEquals("25", resultado.valorPedidos().toPlainString());
    }
}
