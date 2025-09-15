-- Asset types - 0 = IMA, 1 = DIM - but as text '0' not number 0 :)))
--INSERT INTO asset_types (id, code, name) VALUES (1, '0', 'IMA') ON CONFLICT (id) DO NOTHING;
--INSERT INTO asset_types (id, code, name) VALUES (2, '1', 'DIM') ON CONFLICT (id) DO NOTHING;

-- Asset state
--INSERT INTO asset_state (code, description) VALUES ('O', 'OK') ON CONFLICT (code) DO NOTHING;
--INSERT INTO asset_state (code, description) VALUES ('M', 'missing') ON CONFLICT (code) DO NOTHING;
--INSERT INTO asset_state (code, description) VALUES ('V', 'moved') ON CONFLICT (code) DO NOTHING;

INSERT INTO asset_types(id, code, name) VALUES (0, 'IMA');
INSERT INTO asset_types(id, code, name) VALUES (1, 'DIM');

INSERT INTO asset_state(code, description) VALUES ('O', 'OK');
INSERT INTO asset_state(code, description) VALUES ('M', 'missing');
INSERT INTO asset_state(code, description) VALUES ('V', 'moved');

