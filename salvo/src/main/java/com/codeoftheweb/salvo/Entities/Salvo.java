package com.codeoftheweb.salvo.Entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
public class Salvo {

  // ID automatico para la tabla "salvoes"
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  private long id;

  // Relacion con la tabla "gamePlayers"
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "gamePlayer_id")
  private GamePlayer gamePlayer;

  private long turn;

  @ElementCollection
  @Column(name="location")
  private Set<String> locations = new HashSet<>();

  public Salvo(){
  }

  public Salvo(GamePlayer gamePlayer, long turn, String[] locations){
    this.gamePlayer = gamePlayer;
    this.turn = turn;
    this.locations = new HashSet<>(Arrays.asList(locations));
  }

  public long getId() {
    return this.id;
  }

  public GamePlayer getGamePlayer() {
    return this.gamePlayer;
  }

  public long getTurn() {
    return this.turn;
  }

  public Set<String> getLocations() {
    return this.locations;
  }

  // Salida DTO para los objetos Salvo
  public Map<String, Object> toDTO() {
    Map<String, Object> dto = new LinkedHashMap<>();
    dto.put("turn",this.turn);
    dto.put("shots",this.locations);
    return dto;
  }

}
