package com.nexasupplyai.reporting.exception;

import java.util.UUID;

public class SnapshotPanelNoEncontradoException extends RuntimeException {

    public SnapshotPanelNoEncontradoException(UUID id) {
        super("Snapshot del panel no encontrado: " + id);
    }
}