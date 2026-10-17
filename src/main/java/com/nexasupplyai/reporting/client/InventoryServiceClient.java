package com.nexasupplyai.reporting.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Component
public class InventoryServiceClient {
    private final RestClient cliente;

    public InventoryServiceClient(RestClient.Builder builder,
                                  @Value("${clients.inventory-service.base-url:http://localhost:8081}") String url) {
        cliente = builder.baseUrl(url).build();
    }

    public List<Map<String, Object>> listarProductos() { return lista("/api/v1/productos"); }
    public List<Map<String, Object>> listarSaldos() { return lista("/api/v1/saldos-inventario"); }
    public List<Map<String, Object>> listarStockBajo() { return lista("/api/v1/saldos-inventario/stock-bajo"); }

    private List<Map<String, Object>> lista(String uri) {
        List<Map<String, Object>> resultado = cliente.get().uri(uri).retrieve()
                .body(new ParameterizedTypeReference<>() {});
        return resultado == null ? List.of() : resultado;
    }
}
