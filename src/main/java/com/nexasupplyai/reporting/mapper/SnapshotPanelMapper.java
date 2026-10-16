package com.nexasupplyai.reporting.mapper;

import com.nexasupplyai.reporting.dto.request.SnapshotPanelRequest;
import com.nexasupplyai.reporting.dto.response.SnapshotPanelResponse;
import com.nexasupplyai.reporting.entity.SnapshotPanel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SnapshotPanelMapper {

    SnapshotPanel aEntidad(SnapshotPanelRequest request);

    void actualizarEntidad(
            SnapshotPanelRequest request,
            @MappingTarget SnapshotPanel snapshot
    );

    SnapshotPanelResponse aResponse(SnapshotPanel snapshot);
}