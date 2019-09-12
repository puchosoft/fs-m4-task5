package com.codeoftheweb.salvo.Entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Game {

  // ID automatico para la tabla "games"
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  private long id;

  // Relacion con la tabla "gamePlayers"
  @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
  private Set<GamePlayer> gamePlayers;

  private Date creationDate;

  public Game() {
    this.creationDate = new Date();
  }

  public Game(long seconds) {
    seconds = (Math.abs(seconds) > 11*3600 ? 0 : seconds);
    this.creationDate = Date.from(new Date().toInstant().plusSeconds(seconds));
  }

  public Date getCreationDate() {
    return this.creationDate;
  }

  public long getId() {
    return this.id;
  }

  public Set<Player> getPlayers() {
    return this.getGamePlayers().stream()
        .map(gp -> gp.getPlayer()).collect(Collectors.toSet());
  }

  public Set<GamePlayer> getGamePlayers(){
    return this.gamePlayers
        .stream()
        .sorted((gp1,gp2) -> (int)(gp1.getId() - gp2.getId()))
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  // Salida DTO para los objetos Game
  public Map<String, Object> toDTO() {
    Map<String, Object> dto = new LinkedHashMap<>();
    dto.put("id", this.id);
    dto.put("created", this.creationDate);
    dto.put("gamePlayers", this.getGamePlayers()
        .stream()
        .map(gp -> gp.toDTO()));
    return dto;
  }

}
