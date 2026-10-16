package com.nexasupplyai.reporting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "dashboard_snapshots", schema = "reporting")
@Getter
@Setter
@NoArgsConstructor
public class SnapshotPanel {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "period_start", nullable = false)
    private OffsetDateTime inicioPeriodo;

    @Column(name = "period_end", nullable = false)
    private OffsetDateTime finPeriodo;

    @Column(name = "total_products", nullable = false)
    private Long totalProductos = 0L;

    @Column(name = "low_stock_products", nullable = false)
    private Long productosStockBajo = 0L;

    @Column(name = "pending_orders", nullable = false)
    private Long pedidosPendientes = 0L;

    @Column(name = "active_suppliers", nullable = false)
    private Long proveedoresActivos = 0L;

    @Column(name = "inventory_value", nullable = false, precision = 18, scale = 2)
    private BigDecimal valorInventario = BigDecimal.ZERO;

    @Column(name = "orders_value", nullable = false, precision = 18, scale = 2)
    private BigDecimal valorPedidos = BigDecimal.ZERO;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload", nullable = false, columnDefinition = "jsonb")
    private Map<String, Object> contenido = Map.of();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "source_versions", nullable = false, columnDefinition = "jsonb")
    private Map<String, Object> versionesOrigen = Map.of();

    @CreationTimestamp
    @Column(name = "generated_at", nullable = false, updatable = false)
    private OffsetDateTime generadoEn;
}
