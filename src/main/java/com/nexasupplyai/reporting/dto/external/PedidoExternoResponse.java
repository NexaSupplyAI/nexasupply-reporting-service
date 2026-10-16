package com.nexasupplyai.reporting.dto.external;
import java.util.UUID;
public record PedidoExternoResponse(UUID id, String numeroPedido, String estado) {}
