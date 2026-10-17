package com.nexasupplyai.reporting.service.impl;

import com.nexasupplyai.reporting.client.InventoryServiceClient;
import com.nexasupplyai.reporting.client.OrderServiceClient;
import com.nexasupplyai.reporting.client.SupplierServiceClient;
import com.nexasupplyai.reporting.dto.external.*;
import com.nexasupplyai.reporting.dto.response.DashboardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DashboardAggregator {
    private final InventoryServiceClient inventario;
    private final OrderServiceClient pedidos;
    private final SupplierServiceClient proveedores;

    public DashboardResponse agregar() {
        List<ProductoResponse> productos = inventario.listarProductos();
        List<SaldoInventarioResponse> saldos = inventario.listarSaldos();
        List<SaldoInventarioResponse> stockBajo = inventario.listarStockBajo();
        List<PedidoResponse> ordenes = pedidos.listarPedidos();
        List<ProveedorResponse> listaProveedores = proveedores.listarProveedores();
        Map<UUID, BigDecimal> precios = new HashMap<>();
        productos.forEach(p -> precios.put(p.id(), ceroSiNulo(p.precioVenta())));
        BigDecimal valorInventario = saldos.stream()
                .map(s -> ceroSiNulo(s.cantidadEnMano()).multiply(
                        precios.getOrDefault(s.idProducto(), BigDecimal.ZERO)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Set<String> estadosPendientes = Set.of("PENDING", "CONFIRMED", "PROCESSING");
        List<PedidoResponse> pendientes = ordenes.stream()
                .filter(o -> estadosPendientes.contains(o.estado())).toList();
        BigDecimal valorPedidos = ordenes.stream().filter(o -> !"CANCELLED".equals(o.estado()))
                .map(o -> ceroSiNulo(o.montoTotal())).reduce(BigDecimal.ZERO, BigDecimal::add);
        long activos = listaProveedores.stream().filter(p -> "ACTIVE".equals(p.estado())).count();
        long criticos = stockBajo.stream().map(SaldoInventarioResponse::idProducto).distinct().count();
        return new DashboardResponse(productos.size(), criticos, pendientes.size(), activos,
                valorInventario, valorPedidos, stockBajo, pendientes,
                List.of("inventory-service", "order-service", "supplier-service"),
                OffsetDateTime.now(ZoneOffset.UTC));
    }

    private BigDecimal ceroSiNulo(BigDecimal valor) {
        return valor == null ? BigDecimal.ZERO : valor;
    }
}
