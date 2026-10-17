package com.nexasupplyai.reporting.dto.external;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
public record PedidoResponse(UUID id, String numeroPedido, UUID idClienteExterno, String nombreCliente,
        String emailCliente, String telefonoCliente, String estado, String moneda, BigDecimal subtotal,
        BigDecimal montoImpuesto, BigDecimal montoDescuento, BigDecimal montoEnvio, BigDecimal montoTotal,
        String notas, OffsetDateTime solicitadoEn, OffsetDateTime confirmadoEn, OffsetDateTime completadoEn,
        OffsetDateTime canceladoEn, String creadoPor, OffsetDateTime creadoEn, OffsetDateTime actualizadoEn,
        Long version) { }
