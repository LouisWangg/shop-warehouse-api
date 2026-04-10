# Shop Warehouse Management API

A RESTful API built with Java 17 and Spring Boot 3.x for managing warehouse inventory, products, and variants.

## Design Decisions
- **Separation of Concerns:** The system strictly separates generic `Product` catalogs from specific `Variant` items (e.g. sizes and colors). This allows tracking granular stock levels and prices per variant.
- **Relational Integrity:** Implemented a unidirectional Many-to-One relationship from `Variant` to `Product`.
- **JSONB Attributes Mapping:** To ensure the Variant attributes are completely flexible for future expansion (e.g., adding materials, weight), I utilized Hibernate 6's `@JdbcTypeCode(SqlTypes.JSON)` to map a standard Java `Map` directly to a PostgreSQL `jsonb` column.
- **Automated Identifiers:** 
  - Product Codes are strictly auto-generated uppercase substring sequences extracted from the product name (e.g., "Classic T-Shirt" -> "CT").
  - Variant SKUs are uniquely assigned using a 6-digit zero-padded sequence joined to the Product Code (e.g., "CT-000001").
- **Robust Exception Handling:** Integrated spring-boot-starter-validation to proactively reject bad data and built an `@RestControllerAdvice` Global Exception Handler to format errors cleanly.

## Any Assumptions Made
- A "sell" operation is analogous to reducing the stock of a specific variant. This is handled via the `PATCH /api/variants/{id}/stock` endpoint with a negative quantity change parameter.
- A Product does not logically possess a price or stock on its own; it serves strictly as a high-level catalog grouping for its Variants.
- Initial PostgreSQL setup defaults to `postgres` / `postgres`.

## How to Run the Application

### Prerequisites
- **Java 17+**
- **Maven**
- **PostgreSQL** running on port 5432.

### Configuration
By default, the application connects to a PostgreSQL database named `warehouse` with username `postgres` and password `postgres`.
Before booting the application, ensure the database exists:
```sql
CREATE DATABASE warehouse;
```

### Running Locally
Execute the following command in the root project directory:
```bash
mvn spring-boot:run
```

The server will initialize on `http://localhost:8080`.
*(Note: A `SampleDataInitializer` automatically injects dummy Products and Variants formatted with JSON attributes into the database on the first run).*

## API Endpoint Examples

### 1. Products

**Create a Product:**
```bash
curl -X POST http://localhost:8080/api/products \
     -H "Content-Type: application/json" \
     -d '{"name":"Classic T-Shirt","description":"100% cotton"}'
```

**Get All Products:**
```bash
curl -X GET http://localhost:8080/api/products
```

### 2. Variants

**Add a Variant:**
```bash
curl -X POST http://localhost:8080/api/variants/product/1 \
     -H "Content-Type: application/json" \
     -d '{"name":"Red - Medium", "price":199000, "quantity":50, "attributes": {"color": "Red", "size": "M"}}'
```

**Get All Variants (JSON Example Output):**
```bash
curl -X GET http://localhost:8080/api/variants
```
*Output:*
```json
[
  {
    "id": 1,
    "product": {
      "id": 1,
      "name": "Classic T-Shirt",
      "description": "100% cotton base t-shirt",
      "code": "CT"
    },
    "name": "Red - Medium",
    "sku": "CT-000001",
    "price": 199000,
    "quantity": 50,
    "attributes": {
      "size": "M",
      "color": "Red"
    }
  }
]
```

**Record a Sale / Reduce Stock:**
*If a customer buys 2 items, we dynamically query the endpoint with -2:*
```bash
curl -X PATCH "http://localhost:8080/api/variants/1/stock?quantityChange=-2"
```

**Validation Failures:**
*If you submit an empty product name (`""`), the API cleanly rejects it:*
```json
{
  "timestamp": "2026-04-10T15:30:22.0000",
  "status": 400,
  "error": "Validation Failed",
  "details": {
    "name": "Product name is mandatory"
  }
}
```
