-- explicit table creation suitable for H2 (file or mem)

CREATE TABLE IF NOT EXISTS asset_types (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT uq_asset_types_code UNIQUE (code)
);

CREATE TABLE IF NOT EXISTS rooms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT uq_rooms_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS imported_files (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    filename VARCHAR(255) NOT NULL,
    imported_at TIMESTAMP NOT NULL,
    CONSTRAINT uq_imported_files_filename UNIQUE (filename)
);

CREATE TABLE IF NOT EXISTS items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    asset_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    room_id BIGINT,
    type_id BIGINT NOT NULL,
    price DECIMAL(12,2) NOT NULL,
    in_date DATE NOT NULL,
    out_date DATE,
    state VARCHAR(10),
    CONSTRAINT uq_items_asset_id UNIQUE (asset_id),
    CONSTRAINT fk_items_room FOREIGN KEY (room_id) REFERENCES rooms(id),
    CONSTRAINT fk_items_type FOREIGN KEY (type_id) REFERENCES asset_types(id)
);
