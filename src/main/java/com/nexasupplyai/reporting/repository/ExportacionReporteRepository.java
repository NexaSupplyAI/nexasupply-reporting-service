package com.nexasupplyai.reporting.repository;

import com.nexasupplyai.reporting.entity.ExportacionReporte;
import com.nexasupplyai.reporting.enums.EstadoExportacionReporte;
import com.nexasupplyai.reporting.enums.TipoReporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExportacionReporteRepository extends JpaRepository<ExportacionReporte, UUID> {

    List<ExportacionReporte> findAllByEstadoOrderBySolicitadoEnDesc(EstadoExportacionReporte estado);

    List<ExportacionReporte> findAllByTipoReporteOrderBySolicitadoEnDesc(TipoReporte tipoReporte);
}
