package com.nexasupplyai.reporting.storage;

public interface ReportStorage {
    String guardar(String nombre, byte[] contenido, String contentType);
}
