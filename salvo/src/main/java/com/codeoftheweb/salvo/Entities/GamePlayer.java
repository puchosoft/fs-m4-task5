package com.codeoftheweb.salvo.Entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Entity
public class GamePlayer {

  // ID automatico para la tabla "gamePlayers"
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  private long id;

  // Relacion con la tabla "games"
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "game_id")
  private Game game;

  // Relacion con la tabla "players"
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "player_id")
  private Player player;

  // Relacion con la tabla "ships"
  @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
  private Set<Ship> ships;

  // Relacion con la tabla "salvoes"
  @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
  private Set<Salvo> salvoes;

  private Date joinDate;

  public GamePlayer() {
  }

  public GamePlayer(Game game, Player player) {
    this.game = game;
    this.player = player;
    joinDate = new Date();
  }

  public long getId() {
    return this.id;
  }

  public Game getGame() {
    return this.game;
  }

  public Player getPlayer() {
    return this.player;
  }

  public Date getJoinDate() {
    return this.joinDate;
  }

  public Set<Ship> getShips(){
    return this.ships.stream()
        .sorted((s1,s2) -> (int)(s1.getId() - s2.getId()))
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  public ArrayList<Salvo> getSalvoes(){
    return (ArrayList<Salvo>) this.salvoes.stream()
        .sorted((s1,s2) -> (int)(s1.getTurn() - s2.getTurn()))
        .collect(toList());
  }

  // Salida DTO para los objetos GamePlayer
  public Map<String, Object> toDTO() {
    Map<String, Object> dto = new LinkedHashMap<>();
    dto.put("id", this.id);
    dto.put("player", this.player.toDTO());
    return dto;
  }

  public Map<String, Object> toSalvoDTO() {
    Map<String, Object> dto = new LinkedHashMap<>();
    dto.put("playerID", this.player.getId());
    dto.put("turns", this.getSalvoes()
      .stream()
      .map(s -> s.toDTO())
      .collect(toSet())
    );
    return dto;
  }

}
