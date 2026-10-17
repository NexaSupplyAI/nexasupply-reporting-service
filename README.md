# Reporting Service

API propietaria del esquema `reporting`, puerto `8084`. Consolida exclusivamente por REST Inventory, Order y Supplier.

Dashboard actual: `GET /api/v1/reportes/dashboard`. Los snapshots y exportaciones se persisten en su propio esquema. `ReportStorage` selecciona `LocalReportStorage` o `AzureBlobReportStorage` con `REPORT_STORAGE_TYPE=local|azure`.

Variables: URLs de los tres servicios, `REPORT_LOCAL_DIRECTORY`, `REPORT_BLOB_ENDPOINT` y `REPORT_BLOB_CONTAINER=reports`. Azure Blob usa `DefaultAzureCredential`; no usa keys, SAS ni connection strings. En modo local no se crea cliente Azure.

```bash
export SPRING_PROFILES_ACTIVE=local DB_URL=jdbc:postgresql://localhost:5432/nexastock DB_USER=postgres DB_PASSWORD='<local>'
./mvnw clean test
```

OpenAPI: `/v3/api-docs`; health: `/actuator/health`.
