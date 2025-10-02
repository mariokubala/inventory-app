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
     or Windows / Git Bash:
    ./mvnw spring-boot:run

3. **Run**
   ```bash
    java -jar target/inventory-app-0.0.1-SNAPSHOT.jar

The app starts on http://localhost:8080

## Configuration

Database is an H2 file located in mem:inventorydb.

Error log is in ./logs.err.log

1. You can access its console at:
   ```bash
    http://localhost:8080/h2-console

2. Use JDBC URL:
   ```bash
    jdbc:h2:mem:inventorydb

## Usage
1. Load CSV
   ```bash
    GET http://localhost:8080/api/loadFromFile?path=c:/temp/inventuraVzor.csv
    or
    curl -X POST "http://localhost:8080/api/loadFromFile?path=c:/temp/inventuraVzor.csv"

2. Check the table and load .csv file from local c:/temp/
   ```bash
   http://localhost:8080/index.html

3. Test rooms endpoint
   ```bash
    curl http://localhost:8080/api/rooms
    expects JSON array of room names
 
4. Test items/state endpoint
   ```bash
    curl http://localhost:8080/api/items/state/OK
    curl http://localhost:8080/api/items/state/O
    curl http://localhost:8080/api/items/state/missing
    curl http://localhost:8080/api/items/state/removed
    curl http://localhost:8080/api/items/all

5. Browse H2 Console
    ```bash
    Open http://localhost:8080/h2-console
    and login with:
    Driver: org.h2.Driver
    JDBC URL: jdbc:h2:mem:inventorydb
    User: softip / (empty password)

6. Reload same .csv for testing
    ```bash
    Run in console:
    DELETE FROM imported_files;
    DELETE FROM assets;
    - it should delete previous .csv

7. Check that data.sql runs 
    ```bash
    Run in console:
    SELECT * FROM asset_types;
    - you should see the two rows inserted by data.sql

8. Check what is in the DB
    ```bash
    Run in console:
    SELECT * FROM items;        - it shows all lines loaded from .csv file
    SELECT * FROM rooms;        - it shows names for IDs
    SELECT * FROM asset_types;  - it shows names for IDs
