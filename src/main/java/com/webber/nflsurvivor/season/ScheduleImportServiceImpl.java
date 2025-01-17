package com.webber.nflsurvivor.season;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webber.nflsurvivor.game.Game;
import com.webber.nflsurvivor.domain.Stadium;
import com.webber.nflsurvivor.domain.Team;
import com.webber.nflsurvivor.game.GameRepository;
import com.webber.nflsurvivor.repository.StadiumRepository;
import com.webber.nflsurvivor.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
public class ScheduleImportServiceImpl implements ScheduleImportService {

    private static final String ESPN_BASE_URL = "https://site.api.espn.com/apis/site/v2/sports/football/nfl/scoreboard?seasontype=2";

    private final WebClient webClient;

    private final TeamRepository teamRepository;

    private final GameRepository gameRepository;

    private final StadiumRepository stadiumRepository;

    private final SeasonWeekRepository seasonWeekRepository;

    private static final Logger LOG = LoggerFactory.getLogger(ScheduleImportServiceImpl.class);

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmX");



    @Autowired
    public ScheduleImportServiceImpl(WebClient.Builder webClientBuilder, TeamRepository teamRepository, GameRepository gameRepository, StadiumRepository stadiumRepository, SeasonWeekRepository seasonWeekRepository) {
        this.webClient = webClientBuilder.baseUrl(ESPN_BASE_URL)
                .codecs(configurer -> configurer.defaultCodecs()
                .maxInMemorySize(20 * 1024 * 1024))
                .build();
        this.teamRepository = teamRepository;
        this.gameRepository = gameRepository;
        this.stadiumRepository = stadiumRepository;
        this.seasonWeekRepository = seasonWeekRepository;
    }

    @Override
    public Set<Game> importScheduleForWeekAndYear(Integer week, Integer year) {
        return webClient.get()
                .uri(builder -> builder
                        .queryParam("dates", year)
                        .queryParam("week", week)
                        .build())
                .retrieve().bodyToMono(String.class)
                .map(jsonString-> {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        JsonNode jsonNode = mapper.readTree(jsonString);
                        return parseJsonToGames(jsonNode, week);

                    } catch (Exception e) {
                        LOG.error(e.getMessage());
                        return Collections.<Game>emptySet();
                    }
                }).block();
    }

    @Override
    public void importScheduleWeeksForYear(Integer year) {
        webClient.get()
                .uri(builder -> builder
                        .queryParam("dates", year)
                        .build())
                .retrieve().bodyToMono(String.class)
                .map(jsonString-> {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        JsonNode jsonNode = mapper.readTree(jsonString);
                        return parseJsonToWeeks(jsonNode, year);
                    } catch (Exception e) {
                        LOG.error(e.getMessage());
                        return "Error";
                    }
                }).block();
    }

    private String parseJsonToWeeks(JsonNode rootNode, Integer year) {
        JsonNode calendarNode = rootNode
                .path("leagues")
                        .path(0).
                path("calendar");
        JsonNode regularSeasonNode  = findRegularSeasonNode(calendarNode);
        JsonNode entriesNode = regularSeasonNode.get("entries");
        if (entriesNode != null && entriesNode.isArray()) {
            for (JsonNode entry : entriesNode) {
                ZonedDateTime startDateTime = ZonedDateTime.parse(entry.get("startDate").asText(), FORMATTER);
                ZonedDateTime endDateTime = ZonedDateTime.parse(entry.get("endDate").asText(), FORMATTER);

                SeasonWeek seasonWeek = new SeasonWeek(
                        year,
                        entry.get("value").asInt(),
                        startDateTime.toInstant(),
                        endDateTime.toInstant()
                );
                seasonWeekRepository.save(seasonWeek);
            }
        }

        return "OK";
    }

    private JsonNode findRegularSeasonNode(JsonNode calendarNodeArray) {
        for (JsonNode seasonNode : calendarNodeArray) {
            if (seasonNode.has("value") &&
            seasonNode.get("value").asText().equals("2")) {
            return seasonNode;
            }
        }
        return null;
    }

    private Set<Game> parseJsonToGames(JsonNode rootNode, Integer week) {
        Set<Game> allGamesForWeek = new HashSet<>();
        for (JsonNode jsonNode : rootNode.get("events")) {
            String status = jsonNode.get("status").get("type").get("name").asText();
            Iterator<JsonNode> competitionsIterator = jsonNode.get("competitions").iterator();

            allGamesForWeek.add(parseGameFromJsonNode(competitionsIterator.next(), week, status));
        }

        return allGamesForWeek;
    }

    private Game parseGameFromJsonNode(JsonNode competitionNode, Integer week, String status) {
        JsonNode homeTeamNode = competitionNode.get("competitors").get(0);
        JsonNode awayTeamNode = competitionNode.get("competitors").get(1);
        String homeTeamAbbreviation = homeTeamNode.get("team").get("abbreviation").asText();
        String awayTeamAbbreviation = awayTeamNode.get("team").get("abbreviation").asText();
        Team homeTeam = teamRepository.findByAbbreviationIgnoreCase(homeTeamAbbreviation);
        Team awayTeam = teamRepository.findByAbbreviationIgnoreCase(awayTeamAbbreviation);
        long gameEspnId = competitionNode.get("id").asLong();
        JsonNode venueNode = competitionNode.get("venue");
        long venueEspnId = venueNode.get("id").asLong();
        Stadium stadium = stadiumRepository.findByEspnId(venueEspnId);
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(competitionNode.get("date").asText(), FORMATTER);
        double pointSpread = 0.0;
        if (competitionNode.get("odds") != null) {
            JsonNode oddsNode = competitionNode.get("odds");
            pointSpread = oddsNode.get(0).get("spread").asDouble();
        }
        Integer homeScore = homeTeamNode.get("score").asInt();
        Integer awayScore = awayTeamNode.get("score").asInt();
        Game game = gameRepository.findGameByEspnId(gameEspnId);
        if (game == null) {
            game = new Game(homeTeam, awayTeam, week, zonedDateTime.toInstant());
            game.setEspnId(gameEspnId);
        }
        game.setPointSpread(pointSpread);
        game.setHomePoints(homeScore);
        game.setAwayPoints(awayScore);
        game.setVenue(stadium);
        if("STATUS_FINAL".equals(status)) {
            game.setFinished(true);
        }
        gameRepository.save(game);
        return game;
    }

}
