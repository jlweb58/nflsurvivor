CREATE TABLE stadiums (
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    espn_id     BIGINT      NOT NULL,
    name        VARCHAR(255)    NOT NULL,
    zone_id     VARCHAR(255)    NOT NULL,
    CONSTRAINT pk_stadiums PRIMARY KEY (id)

);

ALTER TABLE stadiums
    ADD CONSTRAINT UC_ESPN_ID_STADIUMS UNIQUE (espn_id);

INSERT INTO stadiums (espn_id, name, zone_id)
VALUES
(3752, 'Acrisure Stadium', 'America/New_York'),
(6501, 'Allegiant Stadium', 'America/Los_Angeles'),
(3622,'GEHA Field at Arrowhead Stadium', 'America/Chicago'),
(3687, 'AT&T Stadium',	'America/Chicago'),
(3628,'Bank of America Stadium','America/New_York'),
(3493,'Caesars Superdome','America/Chicago'),
(3937,'Empower Field at Mile High',	'America/Denver'),
(3712,'EverBank Stadium', 'America/New_York'),
(3719,'Northwest Stadium','America/New_York'),
(3727,'Ford Field','America/Detroit'),
(3738,'Gillette Stadium','America/New_York'),
(3948,'Hard Rock Stadium','America/New_York'),
(3883,'Highmark Stadium',   'America/New_York'),
(3679,'Huntington Bank Field','America/New_York'),
(3798,'Lambeau Field',	'America/Chicago'),
(4738,'Levi''s Stadium',		'America/Los_Angeles'),
(3806,'Lincoln Financial Field',		'America/New_York'),
(3812,'Lucas Oil Stadium',     'America/New_York'),
(3673,'Lumen Field',		'America/Los_Angeles'),
(3814,'M&T Bank Stadium',		'America/New_York'),
(5348,'Mercedes-Benz Stadium',	'America/New_York'),
(3839,'MetLife Stadium',	'America/New_York'),
(3810,'Nissan Stadium',		'America/Chicago'),
(3891,'NRG Stadium',		'America/Chicago'),
(3874,'Paycore Stadium',		'America/New_York'),
(3886,'Raymond James Stadium',	'America/New_York'),
(7065,'SoFi Stadium',		'America/Los_Angeles'),
(3933,'Soldier Field',		'America/Chicago'),
(3970,'State Farm Stadium',	'America/Phoenix'),
(5239,'U.S. Bank Stadium',	'America/Chicago'),
(8748,'Arena Corinthians São Paulo, Brazil', 'America/Sao_Paulo'),
(5534,'Tottenham Hotspur Stadium London, U.K.',		'Europe/London'),
(2455,'Wembley Stadium London, U.K.','Europe/London'),
(1775,'Allianz Arena Munich, Germany','Europe/Berlin');
