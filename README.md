# Shop Warehouse Management API

A RESTful API built with Spring Boot 3.x and Java 17 for managing warehouse inventory, products, and their variants.

## Design Decisions
- **Separation of Concerns:** The system separates `Product` (the base item) and `Variant` (specific colors, sizes). This allows tracking granular stock levels and differing prices per variant.
- **Relational Integrity:** Implemented a unidirectional Many-to-One relationship from `Variant` to `Product`.
- **Validation:** Business logic in `VariantService` prevents reducing stock below 0, ensuring we never sell out-of-stock items.
- **Database:** PostgreSQL was selected for robust, relational data persistence.

## Assumptions Made
- A "sell" operation is analogous to reducing the stock of a specific variant. This is handled via the `PATCH /api/variants/{id}/stock` endpoint with a negative quantity change.
- A Product does not have a price or stock on its own; it serves as a catalog grouping for Variants.

## How to Run the Application

### Prerequisites
- **Java 17+**
- **Maven**
- **PostgreSQL** running on port 5432.

### Configuration
By default, the application connects to a PostgreSQL database named `warehouse` with username `postgres` and password `postgres`.
Make sure you create the empty database in your PostgreSQL instance:
```sql
CREATE DATABASE warehouse;
```

### Running Locally
Execute the following command in the root project directory:
```bash
mvn spring-boot:run
```

The server will start on `http://localhost:8080`.
*(Note: Sample data is automatically injected into the database on the first run).*

## API Endpoint Examples

### 1. Products

**Create a Product:**
```bash
curl -X POST http://localhost:8080/api/products \
     -H "Content-Type: application/json" \
     -d '{"name":"Classic T-Shirt","description":"100% cotton","code":"TSH-001"}'
```

**Get All Products:**
```bash
curl -X GET http://localhost:8080/api/products
```

### 2. Variants

**Add a Variant (e.g. Red, Size M):**
```bash
curl -X POST http://localhost:8080/api/variants/product/1 \
     -H "Content-Type: application/json" \
     -d '{"name":"Red - Medium","sku":"TSH-001-R-M","price":19.99,"quantity":50}'
```

**Record a Sale / Reduce Stock:**
*If a customer buys 2 items, we reduce the inventory by 2.*
```bash
curl -X PATCH "http://localhost:8080/api/variants/1/stock?quantityChange=-2"
```

**Prevent Over-selling:**
*If you try to reduce stock by 100 but only 48 remain, the API will reject the request with a `400 Bad Request` and "Insufficient stock" message.*
