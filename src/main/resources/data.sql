INSERT INTO asset_types(code, name) VALUES (0, 'IMA') ON DUPLICATE KEY UPDATE code=code;
INSERT INTO asset_types(code, name) VALUES (1, 'DIM') ON DUPLICATE KEY UPDATE code=code;

INSERT INTO asset_state(code, description) VALUES ('O', 'OK') ON DUPLICATE KEY UPDATE code=code;
INSERT INTO asset_state(code, description) VALUES ('M', 'missing') ON DUPLICATE KEY UPDATE code=code;
INSERT INTO asset_state(code, description) VALUES ('V', 'moved') ON DUPLICATE KEY UPDATE code=code;
