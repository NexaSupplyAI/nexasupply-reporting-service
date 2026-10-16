package com.nexasupplyai.reporting.repository;

import com.nexasupplyai.reporting.entity.SnapshotPanel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface SnapshotPanelRepository extends JpaRepository<SnapshotPanel, UUID> {

    List<SnapshotPanel> findAllByInicioPeriodoGreaterThanEqualAndFinPeriodoLessThanEqualOrderByGeneradoEnDesc(
            OffsetDateTime inicioPeriodo,
            OffsetDateTime finPeriodo
    );

    List<SnapshotPanel> findAllByOrderByGeneradoEnDesc();
}
