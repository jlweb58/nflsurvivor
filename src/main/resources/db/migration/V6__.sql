ALTER TABLE games
    ADD espn_id BIGINT NULL;


ALTER TABLE games
    ADD CONSTRAINT UC_ESPN_ID UNIQUE (espn_id);

ALTER TABLE games
    ADD year INT NOT NULL DEFAULT 2024;

ALTER TABLE games
    ADD finished BOOLEAN DEFAULT FALSE;

ALTER TABLE games
    ADD venue_id BIGINT;

ALTER TABLE games
    ADD CONSTRAINT FK_GAMES_ON_STADIUM FOREIGN KEY (venue_id) REFERENCES stadiums (id);

ALTER TABLE games
    MODIFY venue_id BIGINT NOT NULL;
