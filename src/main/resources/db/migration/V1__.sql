CREATE
SEQUENCE nfl.app_user_seq INCREMENT BY 50 START
WITH 1;

CREATE
SEQUENCE nfl.games_seq INCREMENT BY 50 START
WITH 1;

CREATE
SEQUENCE nfl.team_seq INCREMENT BY 50 START
WITH 1;

CREATE
SEQUENCE nfl.weekly_game_selection_seq INCREMENT BY 50 START
WITH 1;

CREATE TABLE nfl.app_user
(
    id       BIGINT       NOT NULL,
    name     VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT pk_app_user PRIMARY KEY (id)
);

CREATE TABLE nfl.games
(
    id           BIGINT       NOT NULL,
    home_team_id BIGINT       NOT NULL,
    away_team_id BIGINT       NOT NULL,
    week         INT          NOT NULL,
    start_time   VARCHAR(255) NOT NULL,
    home_points  INT          NULL,
    away_points  INT          NULL,
    point_spread DOUBLE       NULL,
    CONSTRAINT pk_games PRIMARY KEY (id)
);

CREATE TABLE nfl.team
(
    id   BIGINT       NOT NULL,
    name VARCHAR(255) NOT NULL,
    logo BLOB         NULL,
    CONSTRAINT pk_team PRIMARY KEY (id)
);

CREATE TABLE nfl.user_roles
(
    user_id    BIGINT       NOT NULL,
    user_roles VARCHAR(255) NULL
);

CREATE TABLE nfl.weekly_game_selection
(
    id               BIGINT       NOT NULL,
    user_id          BIGINT       NOT NULL,
    winning_team_id  BIGINT       NOT NULL,
    game_result      VARCHAR(255) NULL,
    selected_game_id BIGINT       NOT NULL,
    CONSTRAINT pk_weeklygameselection PRIMARY KEY (id)
);

ALTER TABLE nfl.games
    ADD CONSTRAINT FK_GAMES_ON_AWAY_TEAM FOREIGN KEY (away_team_id) REFERENCES nfl.team (id);

ALTER TABLE nfl.games
    ADD CONSTRAINT FK_GAMES_ON_HOME_TEAM FOREIGN KEY (home_team_id) REFERENCES nfl.team (id);

ALTER TABLE nfl.weekly_game_selection
    ADD CONSTRAINT FK_WEEKLYGAMESELECTION_ON_SELECTED_GAME FOREIGN KEY (selected_game_id) REFERENCES nfl.games (id);

ALTER TABLE nfl.weekly_game_selection
    ADD CONSTRAINT FK_WEEKLYGAMESELECTION_ON_USER FOREIGN KEY (user_id) REFERENCES nfl.app_user (id);

ALTER TABLE nfl.weekly_game_selection
    ADD CONSTRAINT FK_WEEKLYGAMESELECTION_ON_WINNING_TEAM FOREIGN KEY (winning_team_id) REFERENCES nfl.team (id);

ALTER TABLE nfl.user_roles
    ADD CONSTRAINT fk_user_roles_on_user FOREIGN KEY (user_id) REFERENCES nfl.app_user (id);