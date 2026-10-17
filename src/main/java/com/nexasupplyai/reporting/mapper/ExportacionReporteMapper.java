package com.nexasupplyai.reporting.mapper;

import com.nexasupplyai.reporting.dto.request.ExportacionReporteRequest;
import com.nexasupplyai.reporting.dto.response.ExportacionReporteResponse;
import com.nexasupplyai.reporting.entity.ExportacionReporte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExportacionReporteMapper {

    @Mapping(target = "filtros", source = "filtros", defaultExpression = "java(java.util.Map.of())")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "uriAlmacenamiento", ignore = true)
    @Mapping(target = "mensajeError", ignore = true)
    @Mapping(target = "solicitadoEn", ignore = true)
    @Mapping(target = "generadoEn", ignore = true)
    ExportacionReporte aEntidad(ExportacionReporteRequest request);

    ExportacionReporteResponse aResponse(ExportacionReporte exportacion);
}
