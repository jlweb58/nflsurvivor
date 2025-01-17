CREATE TABLE season_weeks
(
    id          BIGINT  AUTO_INCREMENT PRIMARY KEY ,
    season_year INT      NOT NULL,
    week_number INT      NOT NULL,
    start_date  VARCHAR(255) NOT NULL,
    end_date    VARCHAR(255) NOT NULL
);
