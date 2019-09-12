package com.codeoftheweb.salvo.Entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
public class Ship {

  // ID automatico para la tabla "ships"
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  private long id;

  private String shipType;

  // Relacion con la tabla "gamePlayers"
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "gamePlayer_id")
  private GamePlayer gamePlayer;

  @ElementCollection
  @Column(name="location")
  private Set<String> locations = new HashSet<>();

  public Ship(){
  }

  public Ship(String shipType, GamePlayer gamePlayer, String[] locations){
    this.shipType = shipType;
    this.gamePlayer = gamePlayer;
    this.locations = new HashSet<>(Arrays.asList(locations));
  }

  public long getId() {
    return this.id;
  }

  public String getShipType() {
    return this.shipType;
  }

  public GamePlayer getGamePlayer() {
    return this.gamePlayer;
  }

  public Set<String> getLocations(){
    return locations;
  }

  // Salida DTO para los objetos Ship
  public Map<String, Object> toDTO() {
    Map<String, Object> dto = new LinkedHashMap<>();
    dto.put("type", this.shipType);
    dto.put("locations", this.locations);
    return dto;
  }

}
