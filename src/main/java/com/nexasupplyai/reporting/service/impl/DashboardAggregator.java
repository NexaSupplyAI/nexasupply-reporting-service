package com.nexasupplyai.reporting.service.impl;

import com.nexasupplyai.reporting.client.InventoryServiceClient;
import com.nexasupplyai.reporting.client.OrderServiceClient;
import com.nexasupplyai.reporting.client.SupplierServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DashboardAggregator {
    private final InventoryServiceClient inventario;
    private final OrderServiceClient pedidos;
    private final SupplierServiceClient proveedores;

    public DatosDashboard agregar() {
        List<Map<String, Object>> productos = inventario.listarProductos();
        List<Map<String, Object>> saldos = inventario.listarSaldos();
        List<Map<String, Object>> stockBajo = inventario.listarStockBajo();
        List<Map<String, Object>> ordenes = pedidos.listarPedidos();
        List<Map<String, Object>> listaProveedores = proveedores.listarProveedores();
        Map<String, BigDecimal> precios = new HashMap<>();
        productos.forEach(p -> precios.put(String.valueOf(p.get("id")), decimal(p.get("precioVenta"))));
        BigDecimal valorInventario = saldos.stream()
                .map(s -> decimal(s.get("cantidadEnMano")).multiply(
                        precios.getOrDefault(String.valueOf(s.get("idProducto")), BigDecimal.ZERO)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Set<String> productosCriticos = new HashSet<>();
        stockBajo.forEach(s -> productosCriticos.add(String.valueOf(s.get("idProducto"))));
        Set<String> estadosPendientes = Set.of("PENDING", "CONFIRMED", "PROCESSING");
        List<Map<String, Object>> pendientes = ordenes.stream()
                .filter(o -> estadosPendientes.contains(String.valueOf(o.get("estado")))).toList();
        BigDecimal valorPedidos = ordenes.stream()
                .filter(o -> !"CANCELLED".equals(String.valueOf(o.get("estado"))))
                .map(o -> decimal(o.get("montoTotal"))).reduce(BigDecimal.ZERO, BigDecimal::add);
        long activos = listaProveedores.stream()
                .filter(p -> "ACTIVE".equals(String.valueOf(p.get("estado")))).count();
        Map<String, Object> contenido = new LinkedHashMap<>();
        contenido.put("stockBajo", stockBajo);
        contenido.put("pedidosPendientes", pendientes);
        contenido.put("generadoDesde", List.of("inventory-service", "order-service", "supplier-service"));
        return new DatosDashboard((long) productos.size(), (long) productosCriticos.size(),
                (long) pendientes.size(), activos, valorInventario, valorPedidos, contenido);
    }

    private BigDecimal decimal(Object valor) {
        if (valor == null) return BigDecimal.ZERO;
        if (valor instanceof BigDecimal decimal) return decimal;
        return new BigDecimal(String.valueOf(valor));
    }

    public record DatosDashboard(long totalProductos, long productosStockBajo, long pedidosPendientes,
                                 long proveedoresActivos, BigDecimal valorInventario, BigDecimal valorPedidos,
                                 Map<String, Object> contenido) {}
}
