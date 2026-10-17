package com.nexasupplyai.reporting.dto.external;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
public record SaldoInventarioResponse(UUID id, UUID idAlmacen, UUID idProducto, BigDecimal cantidadEnMano,
        BigDecimal cantidadReservada, BigDecimal cantidadDisponible, OffsetDateTime actualizadoEn, Long version) { }
