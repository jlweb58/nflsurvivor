ALTER TABLE weekly_game_selections
ADD week INT NOT NULL;

ALTER TABLE weekly_game_selections
    ADD CONSTRAINT UC_USER_ID_WEEK UNIQUE (user_id, week);
