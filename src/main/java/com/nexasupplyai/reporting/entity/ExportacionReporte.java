package com.nexasupplyai.reporting.entity;

import com.nexasupplyai.reporting.enums.EstadoExportacionReporte;
import com.nexasupplyai.reporting.enums.FormatoSalidaReporte;
import com.nexasupplyai.reporting.enums.TipoReporte;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "report_exports", schema = "reporting")
@Getter
@Setter
@NoArgsConstructor
public class ExportacionReporte {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", nullable = false, length = 50)
    private TipoReporte tipoReporte;

    @Enumerated(EnumType.STRING)
    @Column(name = "output_format", nullable = false, length = 10)
    private FormatoSalidaReporte formatoSalida;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private EstadoExportacionReporte estado = EstadoExportacionReporte.PENDIENTE;

    @Column(name = "storage_uri", columnDefinition = "text")
    private String uriAlmacenamiento;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "filters", nullable = false, columnDefinition = "jsonb")
    private Map<String, Object> filtros = Map.of();

    @Column(name = "requested_by", length = 120)
    private String solicitadoPor;

    @Column(name = "error_message", columnDefinition = "text")
    private String mensajeError;

    @CreationTimestamp
    @Column(name = "requested_at", nullable = false, updatable = false)
    private OffsetDateTime solicitadoEn;

    @Column(name = "generated_at")
    private OffsetDateTime generadoEn;

    @Column(name = "expires_at")
    private OffsetDateTime expiraEn;
}
