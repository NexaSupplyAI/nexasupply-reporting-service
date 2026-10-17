# Reporting Service

Microservicio propietario de snapshots del panel y solicitudes de exportación. Utiliza el esquema PostgreSQL `reporting`.

## Responsabilidades

- Generar y persistir snapshots consolidados en vivo.
- Consultar snapshots por período.
- Exportar `CSV`, `JSON`, `XLSX` y `PDF`.
- Guardar archivos localmente o en Azure Blob mediante Managed Identity.

## Configuración

| Variable | Obligatoria | Valor local predeterminado |
|---|---:|---|
| `DB_URL` | No | `jdbc:postgresql://localhost:5432/nexasupplyai` |
| `DB_USER` | No | `postgres` |
| `DB_PASSWORD` | No | `postgres` |
| `JWT_ISSUER_URI` | Producción | `http://localhost:9999` |
| `JWT_JWK_SET_URI` | Producción | `http://localhost:9999/.well-known/jwks.json` |
| `APP_SECURITY_ENABLED` | No | `true` |
| `CORS_ALLOWED_ORIGINS` | No | `http://localhost:4200` |
| `INVENTORY_SERVICE_URL` | No | `http://localhost:8081` |
| `ORDER_SERVICE_URL` | No | `http://localhost:8082` |
| `SUPPLIER_SERVICE_URL` | No | `http://localhost:8083` |
| `REPORT_STORAGE_TYPE` | No | `local`; use `azure` en Azure |
| `REPORT_LOCAL_DIRECTORY` | No | `./build/reports` |
| `REPORT_BLOB_ENDPOINT` | Con Storage Azure | Sin valor |
| `REPORT_BLOB_CONTAINER` | No | `reports` |

Inicializa PostgreSQL mediante [`../nexasupplyai.sql`](../nexasupplyai.sql).

## Ejecución

```bash
export JWT_ISSUER_URI="https://login.microsoftonline.com/<tenant-id>/v2.0"
./mvnw spring-boot:run
```

Puerto: `8084`. Salud pública: `GET /actuator/health`.

### Docker

```bash
docker build -t nexasupply/reporting-service:local .
docker run --rm --add-host=host.docker.internal:host-gateway \
  -p 8084:8084 \
  -e DB_URL=jdbc:postgresql://host.docker.internal:5432/nexasupplyai \
  -e DB_USER=postgres \
  -e DB_PASSWORD=postgres \
  -e JWT_ISSUER_URI=https://login.microsoftonline.com/<tenant-id>/v2.0 \
  -e INVENTORY_SERVICE_URL=http://host.docker.internal:8081 \
  -e ORDER_SERVICE_URL=http://host.docker.internal:8082 \
  -e SUPPLIER_SERVICE_URL=http://host.docker.internal:8083 \
  nexasupply/reporting-service:local
```

## API

| Método | Ruta | Uso |
|---|---|---|
| `GET` | `/api/v1/snapshots-panel` | Listar snapshots |
| `GET` | `/api/v1/snapshots-panel?inicio={ISO-8601}&fin={ISO-8601}` | Filtrar período; ambos parámetros son obligatorios juntos |
| `POST` | `/api/v1/snapshots-panel` | Crear snapshot |
| `POST` | `/api/v1/snapshots-panel/generar` | Consolidar datos en vivo y persistir snapshot |
| `GET/PUT/DELETE` | `/api/v1/snapshots-panel/{id}` | Operaciones por snapshot |
| `GET/POST` | `/api/v1/exportaciones-reportes` | Listar o solicitar exportación |
| `GET/PUT/DELETE` | `/api/v1/exportaciones-reportes/{id}` | Operaciones por exportación |

Tipos de reporte: `DASHBOARD`, `INVENTORY`, `LOW_STOCK`, `ORDERS`, `SUPPLIERS`, `PURCHASE_ORDERS`. Formatos: `CSV`, `PDF`, `XLSX`, `JSON`.

## Pruebas

```bash
./mvnw clean test
./mvnw clean package
```

## Almacenamiento

En local los archivos se escriben en `REPORT_LOCAL_DIRECTORY`. Con `REPORT_STORAGE_TYPE=azure`, el servicio crea el cliente de Blob con `DefaultAzureCredential`; en Container Apps esto utiliza Managed Identity y requiere el rol `Storage Blob Data Contributor` sobre el contenedor o la cuenta.
