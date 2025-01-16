ALTER TABLE teams
    ADD home_stadium_id BIGINT;

ALTER TABLE teams
ADD CONSTRAINT FK_TEAMS_STADIUM FOREIGN KEY (home_stadium_id) REFERENCES stadiums (id);


UPDATE teams
SET teams.home_stadium_id = 1
WHERE teams.name = 'Steelers';

UPDATE teams
SET teams.home_stadium_id = 2
WHERE teams.name = 'Raiders';

UPDATE teams
SET teams.home_stadium_id = 3
WHERE teams.name = 'Chiefs';

UPDATE teams
SET teams.home_stadium_id = 4
WHERE teams.name = 'Cowboys';

UPDATE teams
SET teams.home_stadium_id = 5
WHERE teams.name = 'Panthers';

UPDATE teams
SET teams.home_stadium_id = 6
WHERE teams.name = 'Saints';

UPDATE teams
SET teams.home_stadium_id = 7
WHERE teams.name = 'Broncos';

UPDATE teams
SET teams.home_stadium_id = 8
WHERE teams.name = 'Jaguars';

UPDATE teams
SET teams.home_stadium_id = 9
WHERE teams.name = 'Commanders';

UPDATE teams
SET teams.home_stadium_id = 10
WHERE teams.name = 'Lions';

UPDATE teams
SET teams.home_stadium_id = 11
WHERE teams.name = 'Patriots';

UPDATE teams
SET teams.home_stadium_id = 12
WHERE teams.name = 'Dolphins';

UPDATE teams
SET teams.home_stadium_id = 13
WHERE teams.name = 'Bills';

UPDATE teams
SET teams.home_stadium_id = 14
WHERE teams.name = 'Browns';

UPDATE teams
SET teams.home_stadium_id = 15
WHERE teams.name = 'Packers';

UPDATE teams
SET teams.home_stadium_id = 16
WHERE teams.name = '49ers';

UPDATE teams
SET teams.home_stadium_id = 17
WHERE teams.name = 'Eagles';

UPDATE teams
SET teams.home_stadium_id = 18
WHERE teams.name = 'Colts';

UPDATE teams
SET teams.home_stadium_id = 19
WHERE teams.name = 'Seahawks';

UPDATE teams
SET teams.home_stadium_id = 20
WHERE teams.name = 'Ravens';

UPDATE teams
SET teams.home_stadium_id = 21
WHERE teams.name = 'Falcons';

UPDATE teams
SET teams.home_stadium_id = 22
WHERE teams.name = 'Giants';

UPDATE teams
SET teams.home_stadium_id = 22
WHERE teams.name = 'Jets';

UPDATE teams
SET teams.home_stadium_id = 23
WHERE teams.name = 'Titans';

UPDATE teams
SET teams.home_stadium_id = 24
WHERE teams.name = 'Texans';

UPDATE teams
SET teams.home_stadium_id = 25
WHERE teams.name = 'Bengals';

UPDATE teams
SET teams.home_stadium_id = 26
WHERE teams.name = 'Bucs';

UPDATE teams
SET teams.home_stadium_id = 27
WHERE teams.name = 'Rams';

UPDATE teams
SET teams.home_stadium_id = 27
WHERE teams.name = 'Chargers';

UPDATE teams
SET teams.home_stadium_id = 28
WHERE teams.name = 'Bears';

UPDATE teams
SET teams.home_stadium_id = 29
WHERE teams.name = 'Cardinals';

UPDATE teams
SET teams.home_stadium_id = 30
WHERE teams.name = 'Vikings';