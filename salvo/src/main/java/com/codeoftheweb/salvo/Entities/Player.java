package com.codeoftheweb.salvo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
public class Player {

  // ID automatico para la tabla "players"
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  private long id;

  private String username;

  // Relacion con la tabla "gamePlayers"
  @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
  private Set<GamePlayer> gamePlayers;

  // Relacion con la tabla "scores"
  @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
  private Set<Score> scores;

  public Player() {
  }

  public Player(String username) {
    this.username = username;
  }

  public long getId() {
    return this.id;
  }

  public String getUsername() {
    return this.username;
  }

  @JsonIgnore
  public List<Game> getGames() {
    return gamePlayers
        .stream()
        .sorted((gp1,gp2) -> (int)(gp1.getId() - gp2.getId()))
        .map(gp -> gp.getGame())
        .collect(toList());
  }

  public Score getGameScore(Game game) {
    return this.scores
        .stream()
        .filter(score -> score.getGame().equals(game))
        .findFirst().orElse(null);
  }

  // Salida DTO para los objetos Player
  public Map<String, Object> toDTO() {
    Map<String, Object> dto = new LinkedHashMap<>();
    dto.put("id", this.id);
    dto.put("email", this.username);
    return dto;
  }

}
