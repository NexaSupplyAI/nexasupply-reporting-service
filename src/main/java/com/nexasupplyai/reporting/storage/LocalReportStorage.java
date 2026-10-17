package com.nexasupplyai.reporting.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@ConditionalOnProperty(name = "reports.storage.type", havingValue = "local", matchIfMissing = true)
public class LocalReportStorage implements ReportStorage {
    private final Path directorio;
    public LocalReportStorage(@Value("${reports.storage.local-directory:./build/reports}") String directorio) {
        this.directorio = Path.of(directorio).toAbsolutePath().normalize();
    }
    @Override
    public String guardar(String nombre, byte[] contenido, String contentType) {
        try {
            Files.createDirectories(directorio);
            Path destino = directorio.resolve(nombre).normalize();
            if (!destino.startsWith(directorio)) throw new IllegalArgumentException("Nombre de reporte inválido");
            Files.createDirectories(destino.getParent());
            Files.write(destino, contenido);
            return destino.toUri().toString();
        } catch (IOException ex) {
            throw new IllegalStateException("No fue posible almacenar el reporte", ex);
        }
    }
}
