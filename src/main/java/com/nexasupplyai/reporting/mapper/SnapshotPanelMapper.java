package com.nexasupplyai.reporting.mapper;

import com.nexasupplyai.reporting.dto.request.SnapshotPanelRequest;
import com.nexasupplyai.reporting.dto.response.SnapshotPanelResponse;
import com.nexasupplyai.reporting.entity.SnapshotPanel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SnapshotPanelMapper {

    @Mapping(target = "contenido", source = "contenido", defaultExpression = "java(java.util.Map.of())")
    @Mapping(target = "versionesOrigen", source = "versionesOrigen", defaultExpression = "java(java.util.Map.of())")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "generadoEn", ignore = true)
    SnapshotPanel aEntidad(SnapshotPanelRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "generadoEn", ignore = true)
    void actualizarEntidad(
            SnapshotPanelRequest request,
            @MappingTarget SnapshotPanel snapshot
    );

    SnapshotPanelResponse aResponse(SnapshotPanel snapshot);
}
