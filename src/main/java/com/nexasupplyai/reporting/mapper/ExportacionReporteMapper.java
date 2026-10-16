package com.nexasupplyai.reporting.mapper;

import com.nexasupplyai.reporting.dto.request.ExportacionReporteRequest;
import com.nexasupplyai.reporting.dto.response.ExportacionReporteResponse;
import com.nexasupplyai.reporting.entity.ExportacionReporte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ExportacionReporteMapper {

    @Mapping(target = "filtros", source = "filtros", defaultExpression = "java(java.util.Map.of())")
    ExportacionReporte aEntidad(ExportacionReporteRequest request);

    void actualizarEntidad(
            ExportacionReporteRequest request,
            @MappingTarget ExportacionReporte exportacion
    );

    ExportacionReporteResponse aResponse(ExportacionReporte exportacion);
}
