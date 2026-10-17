package com.nexasupplyai.reporting.client;

import com.nexasupplyai.reporting.dto.external.ProductoResponse;
import com.nexasupplyai.reporting.dto.external.SaldoInventarioResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class InventoryServiceClient {
    private final RestClient cliente;

    public InventoryServiceClient(RestClient.Builder builder,
                                  @Value("${clients.inventory-service.base-url:http://localhost:8081}") String url) {
        cliente = builder.baseUrl(url).build();
    }

    public List<ProductoResponse> listarProductos() {
        List<ProductoResponse> resultado = cliente.get().uri("/api/v1/productos").retrieve()
                .body(new ParameterizedTypeReference<>() {});
        return resultado == null ? List.of() : resultado;
    }
    public List<SaldoInventarioResponse> listarSaldos() { return saldos("/api/v1/saldos-inventario"); }
    public List<SaldoInventarioResponse> listarStockBajo() { return saldos("/api/v1/saldos-inventario/stock-bajo"); }
    private List<SaldoInventarioResponse> saldos(String uri) {
        List<SaldoInventarioResponse> resultado = cliente.get().uri(uri).retrieve()
                .body(new ParameterizedTypeReference<>() {});
        return resultado == null ? List.of() : resultado;
    }
}
