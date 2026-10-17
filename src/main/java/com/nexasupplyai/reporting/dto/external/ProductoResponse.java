package com.nexasupplyai.reporting.dto.external;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;
public record ProductoResponse(UUID id, String sku, String codigoBarras, String nombre, String descripcion,
        UUID idCategoria, String unidadMedida, BigDecimal precioVenta, BigDecimal stockMinimo,
        BigDecimal puntoReorden, BigDecimal cantidadReorden, Boolean activo, Map<String,Object> atributos,
        OffsetDateTime creadoEn, OffsetDateTime actualizadoEn, Long version) { }
