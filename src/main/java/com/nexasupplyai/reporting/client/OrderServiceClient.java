package com.nexasupplyai.reporting.client;

import com.nexasupplyai.reporting.dto.external.PedidoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class OrderServiceClient {
    private final RestClient cliente;
    public OrderServiceClient(RestClient.Builder builder,
                              @Value("${clients.order-service.base-url:http://localhost:8082}") String url) {
        cliente = builder.baseUrl(url).build();
    }
    public List<PedidoResponse> listarPedidos() {
        List<PedidoResponse> resultado = cliente.get().uri("/api/v1/ordenes").retrieve()
                .body(new ParameterizedTypeReference<>() {});
        return resultado == null ? List.of() : resultado;
    }
}
