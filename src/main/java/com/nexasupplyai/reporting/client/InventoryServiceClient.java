package com.nexasupplyai.reporting.client;
import com.nexasupplyai.reporting.dto.external.ProductoInventarioResponse;
import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Component; import org.springframework.web.client.RestClient; import java.util.UUID;
@Component public class InventoryServiceClient { private final RestClient cliente; public InventoryServiceClient(RestClient.Builder builder,@Value("${clients.inventory-service.base-url}") String url){cliente=builder.baseUrl(url).build();} public ProductoInventarioResponse obtenerProducto(UUID id){return cliente.get().uri("/api/v1/productos/{id}",id).retrieve().body(ProductoInventarioResponse.class);} }
