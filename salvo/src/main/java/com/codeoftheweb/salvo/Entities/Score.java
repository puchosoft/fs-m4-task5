package com.codeoftheweb.salvo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Score {

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

  private float score;
  private Date finishDate;

  public Score() {}

  public Score(Game game, Player player, float score) {
    this.game = game;
    this.player = player;
    this.score = score;
    this.finishDate = new Date();
  }

  public long getId(){
    return this.id;
  }

  @JsonIgnore
  public Game getGame() {
    return this.game;
  }

  public Player getPlayer() {
    return this.player;
  }

  public Date getFinishDate() {
    return this.finishDate;
  }

  public float getScore(){
    return this.score;
  }

}
