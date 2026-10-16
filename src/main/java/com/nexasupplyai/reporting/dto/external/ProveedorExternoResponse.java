package com.nexasupplyai.reporting.dto.external;
import java.util.UUID;
public record ProveedorExternoResponse(UUID id, String codigoProveedor, String razonSocial, String estado) {}
