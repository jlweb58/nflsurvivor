CREATE SEQUENCE app_users_seq INCREMENT BY 50 START WITH 1;

CREATE SEQUENCE games_seq INCREMENT BY 50 START WITH 1;

CREATE SEQUENCE teams_seq INCREMENT BY 50 START WITH 1;

CREATE SEQUENCE weekly_game_selections_seq INCREMENT BY 50 START WITH 1;

CREATE TABLE app_users
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    name     VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT pk_app_users PRIMARY KEY (id)
);

CREATE TABLE games
(
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    home_team_id BIGINT       NOT NULL,
    away_team_id BIGINT       NOT NULL,
    week         INT          NOT NULL,
    start_time   VARCHAR(255) NOT NULL,
    home_points  INT          NULL,
    away_points  INT          NULL,
    point_spread DOUBLE       NULL,
    CONSTRAINT pk_games PRIMARY KEY (id)
);

CREATE TABLE teams
(
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    name         VARCHAR(255) NOT NULL,
    abbreviation VARCHAR(255) NOT NULL,
    CONSTRAINT pk_teams PRIMARY KEY (id)
);

CREATE TABLE weekly_game_selections
(
    id               BIGINT       NOT NULL AUTO_INCREMENT,
    user_id          BIGINT       NOT NULL,
    winning_team_id  BIGINT       NOT NULL,
    game_result      VARCHAR(255) NULL,
    selected_game_id BIGINT       NOT NULL,
    CONSTRAINT pk_weekly_game_selections PRIMARY KEY (id)
);

ALTER TABLE games
    ADD CONSTRAINT FK_GAMES_ON_AWAY_TEAM FOREIGN KEY (away_team_id) REFERENCES teams (id);

ALTER TABLE games
    ADD CONSTRAINT FK_GAMES_ON_HOME_TEAM FOREIGN KEY (home_team_id) REFERENCES teams (id);

ALTER TABLE weekly_game_selections
    ADD CONSTRAINT FK_WEEKLY_GAME_SELECTIONS_ON_SELECTED_GAME FOREIGN KEY (selected_game_id) REFERENCES games (id);

ALTER TABLE weekly_game_selections
    ADD CONSTRAINT FK_WEEKLY_GAME_SELECTIONS_ON_USER FOREIGN KEY (user_id) REFERENCES app_users (id);

ALTER TABLE weekly_game_selections
    ADD CONSTRAINT FK_WEEKLY_GAME_SELECTIONS_ON_WINNING_TEAM FOREIGN KEY (winning_team_id) REFERENCES teams (id);
