package com.nexasupplyai.reporting.storage;

import com.azure.core.util.BinaryData;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "reports.storage.type", havingValue = "azure")
public class AzureBlobReportStorage implements ReportStorage {
    private final BlobContainerClient contenedor;
    public AzureBlobReportStorage(@Value("${reports.storage.blob-endpoint}") String endpoint,
                                  @Value("${reports.storage.blob-container:reports}") String nombreContenedor) {
        if (endpoint == null || endpoint.isBlank())
            throw new IllegalStateException("REPORT_BLOB_ENDPOINT es obligatorio cuando REPORT_STORAGE_TYPE=azure");
        contenedor = new BlobServiceClientBuilder().endpoint(endpoint)
                .credential(new DefaultAzureCredentialBuilder().build()).buildClient()
                .getBlobContainerClient(nombreContenedor);
    }
    @Override
    public String guardar(String nombre, byte[] contenido, String contentType) {
        var blob = contenedor.getBlobClient(nombre);
        blob.upload(BinaryData.fromBytes(contenido), true);
        blob.setHttpHeaders(new BlobHttpHeaders().setContentType(contentType));
        return blob.getBlobUrl();
    }
}
