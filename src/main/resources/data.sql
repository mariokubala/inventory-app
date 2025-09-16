-- insert initial lookup values
-- Asset types - 0 = IMA, 1 = DIM - but as text '0' not number 0 :)))
INSERT INTO asset_types(code, name) VALUES (0, 'IMA');
INSERT INTO asset_types(code, name) VALUES (1, 'DIM');

-- optional demo rooms (but not needed)
INSERT INTO rooms(name) VALUES ('Room1');
INSERT INTO rooms(name) VALUES ('Room2');

-- Asset state - not needed now
--INSERT INTO asset_state (code, description) VALUES ('O', 'OK') ON CONFLICT (code) DO NOTHING;
--INSERT INTO asset_state (code, description) VALUES ('M', 'missing') ON CONFLICT (code) DO NOTHING;
--INSERT INTO asset_state (code, description) VALUES ('V', 'moved') ON CONFLICT (code) DO NOTHING;
