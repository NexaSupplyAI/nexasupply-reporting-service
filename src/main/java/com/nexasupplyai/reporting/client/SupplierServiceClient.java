package com.nexasupplyai.reporting.client;
import com.nexasupplyai.reporting.dto.external.ProveedorExternoResponse;
import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Component; import org.springframework.web.client.RestClient; import java.util.UUID;
@Component public class SupplierServiceClient { private final RestClient cliente; public SupplierServiceClient(RestClient.Builder builder,@Value("${clients.supplier-service.base-url:http://localhost:8083}") String url){cliente=builder.baseUrl(url).build();} public ProveedorExternoResponse obtenerProveedor(UUID id){return cliente.get().uri("/api/v1/proveedores/{id}",id).retrieve().body(ProveedorExternoResponse.class);} }
