package com.nexasupplyai.reporting.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Component
public class SupplierServiceClient {
    private final RestClient cliente;
    public SupplierServiceClient(RestClient.Builder builder,
                                 @Value("${clients.supplier-service.base-url:http://localhost:8083}") String url) {
        cliente = builder.baseUrl(url).build();
    }
    public List<Map<String, Object>> listarProveedores() { return lista("/api/v1/proveedores"); }
    public List<Map<String, Object>> listarOrdenesCompra() { return lista("/api/v1/ordenes-compra"); }
    private List<Map<String, Object>> lista(String uri) {
        List<Map<String, Object>> resultado = cliente.get().uri(uri).retrieve()
                .body(new ParameterizedTypeReference<>() {});
        return resultado == null ? List.of() : resultado;
    }
}
