package com.nexasupplyai.reporting.client;
import com.nexasupplyai.reporting.dto.external.PedidoExternoResponse;
import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Component; import org.springframework.web.client.RestClient; import java.util.UUID;
@Component public class OrderServiceClient { private final RestClient cliente; public OrderServiceClient(RestClient.Builder builder,@Value("${clients.order-service.base-url:http://localhost:8082}") String url){cliente=builder.baseUrl(url).build();} public PedidoExternoResponse obtenerPedido(UUID id){return cliente.get().uri("/api/v1/ordenes/{id}",id).retrieve().body(PedidoExternoResponse.class);} }
