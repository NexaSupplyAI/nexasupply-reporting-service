package com.nexasupplyai.reporting.exception;

import java.util.UUID;

public class ExportacionReporteNoEncontradaException extends RuntimeException {

    public ExportacionReporteNoEncontradaException(UUID id) {
        super("Exportación de reporte no encontrada: " + id);
    }
}