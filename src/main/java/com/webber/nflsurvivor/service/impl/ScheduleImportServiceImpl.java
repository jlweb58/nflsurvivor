package com.webber.nflsurvivor.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webber.nflsurvivor.game.Game;
import com.webber.nflsurvivor.domain.Stadium;
import com.webber.nflsurvivor.domain.Team;
import com.webber.nflsurvivor.game.GameRepository;
import com.webber.nflsurvivor.repository.StadiumRepository;
import com.webber.nflsurvivor.repository.TeamRepository;
import com.webber.nflsurvivor.service.ScheduleImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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

    private static final Logger LOG = LoggerFactory.getLogger(ScheduleImportServiceImpl.class);

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmX");



    @Autowired
    public ScheduleImportServiceImpl(WebClient.Builder webClientBuilder, TeamRepository teamRepository, GameRepository gameRepository, StadiumRepository stadiumRepository) {
        this.webClient = webClientBuilder.baseUrl(ESPN_BASE_URL).build();
        this.teamRepository = teamRepository;
        this.gameRepository = gameRepository;
        this.stadiumRepository = stadiumRepository;
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
