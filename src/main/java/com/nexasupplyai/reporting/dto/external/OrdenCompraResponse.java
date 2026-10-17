package com.nexasupplyai.reporting.dto.external;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
public record OrdenCompraResponse(UUID id, String numeroOrdenCompra, UUID idProveedor, String estado, String moneda,
        BigDecimal subtotal, BigDecimal montoImpuesto, BigDecimal montoDescuento, BigDecimal montoTotal,
        LocalDate fechaEntregaEsperada, String notas, String solicitadoPor, String aprobadoPor,
        OffsetDateTime aprobadoEn, OffsetDateTime creadoEn, OffsetDateTime actualizadoEn, Long version) { }
