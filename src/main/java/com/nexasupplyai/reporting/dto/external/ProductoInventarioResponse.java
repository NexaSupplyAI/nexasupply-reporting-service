package com.nexasupplyai.reporting.dto.external;
import java.util.UUID;
public record ProductoInventarioResponse(UUID id, String sku, String nombre, Boolean activo) {}
