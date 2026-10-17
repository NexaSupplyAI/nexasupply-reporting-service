package com.nexasupplyai.reporting.repository;

import com.nexasupplyai.reporting.entity.ExportacionReporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExportacionReporteRepository extends JpaRepository<ExportacionReporte, UUID> {
}
