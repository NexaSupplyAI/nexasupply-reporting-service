package com.nexasupplyai.reporting.service.impl;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.nexasupplyai.reporting.client.InventoryServiceClient;
import com.nexasupplyai.reporting.client.OrderServiceClient;
import com.nexasupplyai.reporting.client.SupplierServiceClient;
import com.nexasupplyai.reporting.dto.request.ExportacionReporteRequest;
import com.nexasupplyai.reporting.dto.response.ExportacionReporteResponse;
import com.nexasupplyai.reporting.entity.ExportacionReporte;
import com.nexasupplyai.reporting.enums.EstadoExportacionReporte;
import com.nexasupplyai.reporting.enums.FormatoSalidaReporte;
import com.nexasupplyai.reporting.exception.ExportacionReporteNoEncontradaException;
import com.nexasupplyai.reporting.mapper.ExportacionReporteMapper;
import com.nexasupplyai.reporting.repository.ExportacionReporteRepository;
import com.nexasupplyai.reporting.service.interfaces.ExportacionReporteServiceInterface;
import com.nexasupplyai.reporting.storage.ReportStorage;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExportacionReporteServiceImpl implements ExportacionReporteServiceInterface {
    private final ExportacionReporteRepository repositorio;
    private final ExportacionReporteMapper mapper;
    private final InventoryServiceClient inventario;
    private final OrderServiceClient pedidos;
    private final SupplierServiceClient proveedores;
    private final SnapshotPanelServiceImpl snapshots;
    private final ReportStorage storage;
    private final ObjectMapper objectMapper;

    @Override
    public List<ExportacionReporteResponse> listar() {
        return repositorio.findAll().stream().map(mapper::aResponse).toList();
    }

    @Override
    public ExportacionReporteResponse obtenerPorId(UUID id) { return mapper.aResponse(buscar(id)); }

    @Override
    @Transactional
    public ExportacionReporteResponse crear(ExportacionReporteRequest request) {
        ExportacionReporte exportacion = mapper.aEntidad(request);
        exportacion.setEstado(EstadoExportacionReporte.PROCESSING);
        exportacion = repositorio.saveAndFlush(exportacion);
        try {
            Object datos = obtenerDatos(request);
            Archivo archivo = serializar(datos, request.formatoSalida());
            String nombre = request.tipoReporte().name().toLowerCase(Locale.ROOT) + "/"
                    + exportacion.getId() + "." + archivo.extension();
            exportacion.setUriAlmacenamiento(storage.guardar(nombre, archivo.contenido(), archivo.contentType()));
            exportacion.setEstado(EstadoExportacionReporte.COMPLETED);
            exportacion.setGeneradoEn(OffsetDateTime.now(ZoneOffset.UTC));
            exportacion.setMensajeError(null);
        } catch (Exception ex) {
            exportacion.setEstado(EstadoExportacionReporte.FAILED);
            exportacion.setMensajeError(ex.getMessage() == null ? "Falló la generación" : ex.getMessage());
        }
        return mapper.aResponse(repositorio.save(exportacion));
    }

    @Override
    @Transactional
    public ExportacionReporteResponse actualizar(UUID id, ExportacionReporteRequest request) {
        throw new IllegalArgumentException("Una exportación es inmutable; solicite una nueva generación");
    }

    @Override
    @Transactional
    public void eliminar(UUID id) { repositorio.delete(buscar(id)); }

    private Object obtenerDatos(ExportacionReporteRequest request) {
        return switch (request.tipoReporte()) {
            case DASHBOARD -> snapshots.generar();
            case INVENTORY -> inventario.listarSaldos();
            case LOW_STOCK -> inventario.listarStockBajo();
            case ORDERS -> pedidos.listarPedidos();
            case SUPPLIERS -> proveedores.listarProveedores();
            case PURCHASE_ORDERS -> proveedores.listarOrdenesCompra();
        };
    }

    private Archivo serializar(Object datos, FormatoSalidaReporte formato) throws Exception {
        return switch (formato) {
            case JSON -> new Archivo(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(datos),
                    "json", "application/json");
            case CSV -> new Archivo(csv(datos).getBytes(StandardCharsets.UTF_8), "csv", "text/csv; charset=utf-8");
            case XLSX -> new Archivo(xlsx(datos), "xlsx",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            case PDF -> new Archivo(pdf(datos), "pdf", "application/pdf");
        };
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> filas(Object datos) {
        Object convertido = objectMapper.convertValue(datos, Object.class);
        if (convertido instanceof List<?> lista) {
            return lista.stream().map(v -> v instanceof Map<?, ?> mapa
                    ? (Map<String, Object>) mapa : Map.of("valor", v)).toList();
        }
        if (convertido instanceof Map<?, ?> mapa) return List.of((Map<String, Object>) mapa);
        return List.of(Map.of("valor", String.valueOf(convertido)));
    }

    private String csv(Object datos) {
        List<Map<String, Object>> filas = filas(datos);
        if (filas.isEmpty()) return "";
        LinkedHashSet<String> columnas = new LinkedHashSet<>();
        filas.forEach(f -> columnas.addAll(f.keySet()));
        StringBuilder csv = new StringBuilder(String.join(",", columnas)).append('\n');
        for (Map<String, Object> fila : filas) {
            csv.append(columnas.stream().map(c -> escaparCsv(fila.get(c))).collect(java.util.stream.Collectors.joining(",")))
                    .append('\n');
        }
        return csv.toString();
    }

    private String escaparCsv(Object valor) {
        String texto = valor == null ? "" : String.valueOf(valor);
        return '"' + texto.replace("\"", "\"\"") + '"';
    }

    private byte[] xlsx(Object datos) throws Exception {
        List<Map<String, Object>> filas = filas(datos);
        try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream salida = new ByteArrayOutputStream()) {
            var hoja = workbook.createSheet("reporte");
            LinkedHashSet<String> columnas = new LinkedHashSet<>();
            filas.forEach(f -> columnas.addAll(f.keySet()));
            var cabecera = hoja.createRow(0);
            int columna = 0;
            for (String nombre : columnas) cabecera.createCell(columna++).setCellValue(nombre);
            int filaNumero = 1;
            for (Map<String, Object> fila : filas) {
                var filaExcel = hoja.createRow(filaNumero++);
                columna = 0;
                for (String nombre : columnas) filaExcel.createCell(columna++).setCellValue(String.valueOf(fila.getOrDefault(nombre, "")));
            }
            workbook.write(salida);
            return salida.toByteArray();
        }
    }

    private byte[] pdf(Object datos) throws Exception {
        try (ByteArrayOutputStream salida = new ByteArrayOutputStream()) {
            Document documento = new Document();
            PdfWriter.getInstance(documento, salida);
            documento.open();
            documento.add(new Paragraph("NexaStock AI - Reporte"));
            documento.add(new Paragraph(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(datos)));
            documento.close();
            return salida.toByteArray();
        }
    }

    private ExportacionReporte buscar(UUID id) {
        return repositorio.findById(id).orElseThrow(() -> new ExportacionReporteNoEncontradaException(id));
    }

    private record Archivo(byte[] contenido, String extension, String contentType) {}
}
