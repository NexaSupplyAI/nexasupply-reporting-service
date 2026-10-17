package com.nexasupplyai.reporting.dto.external;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
public record ProveedorResponse(UUID id, String codigoProveedor, String numeroFiscal, String razonSocial,
        String nombreComercial, String email, String telefono, String estado, BigDecimal calificacion,
        Integer diasEntregaPredeterminados, String direccion, String distrito, String ciudad, String region,
        String codigoPais, OffsetDateTime creadoEn, OffsetDateTime actualizadoEn, Long version) { }
