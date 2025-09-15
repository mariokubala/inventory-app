# Inventory App

Spring Boot application for loading and viewing inventory data.

## Build & Run

1. **Clone repository**

   ```bash
   git clone https://github.com/mariokubala/inventory-app.git
   cd inventory-app

2. **Build**

   ```bash
    mvnw clean package

3. **Run**

   ```bash
    java -jar target/inventory-app-0.0.1-SNAPSHOT.jar

The app starts on http://localhost:8080

## Configuration

Database is an H2 file located in ./data/inventorydb.

Error log is in ./logs.err.log

1. You can access its console at:

   ```bash
    http://localhost:8080/h2-console

2. Use JDBC URL:

   ```bash
    jdbc:h2:file:./data/inventorydb

## Usage
1. Load CSV

   ```bash
    GET http://localhost:8080/api/loadFromFile?path=c:/temp/inventuraVzor.csv
    or
    curl -X POST "http://localhost:8080/api/loadFromFile?path=c:/temp/inventuraVzor.csv"

2. Browse H2 Console

   ```bash
   Open http://localhost:8080/h2-console
   and login with:
   Driver: org.h2.Driver
   JDBC URL: jdbc:h2:file:./data/inventorydb
   User: softip / (empty password)

3. Reload same .csv for testing
   ```bash
    Run in console:
    DELETE FROM imported_files;
    DELETE FROM assets;
