package com.nexasupplyai.reporting.client;

import com.nexasupplyai.reporting.dto.external.OrdenCompraResponse;
import com.nexasupplyai.reporting.dto.external.ProveedorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class SupplierServiceClient {
    private final RestClient cliente;
    public SupplierServiceClient(RestClient.Builder builder,
                                 @Value("${clients.supplier-service.base-url:http://localhost:8083}") String url) {
        cliente = builder.baseUrl(url).build();
    }
    public List<ProveedorResponse> listarProveedores() {
        List<ProveedorResponse> resultado = cliente.get().uri("/api/v1/proveedores").retrieve()
                .body(new ParameterizedTypeReference<>() {});
        return resultado == null ? List.of() : resultado;
    }
    public List<OrdenCompraResponse> listarOrdenesCompra() {
        List<OrdenCompraResponse> resultado = cliente.get().uri("/api/v1/ordenes-compra").retrieve()
                .body(new ParameterizedTypeReference<>() {});
        return resultado == null ? List.of() : resultado;
    }
}
