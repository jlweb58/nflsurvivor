ALTER TABLE games
    ADD espn_id BIGINT NULL;


ALTER TABLE games
    ADD CONSTRAINT UC_ESPN_ID UNIQUE (espn_id);

ALTER TABLE games
    ADD year INT NOT NULL DEFAULT 2024;