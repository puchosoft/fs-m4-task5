package com.codeoftheweb.salvo.Controllers;

import com.codeoftheweb.salvo.Entities.GamePlayer;
import com.codeoftheweb.salvo.Repositories.GamePlayerRepository;
import com.codeoftheweb.salvo.Repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/api") // Todos los controladores cuelgan de /api
public class SalvoController {

  @Autowired
  private GameRepository gRepository;

  @Autowired
  private GamePlayerRepository gpRepository;

  // Genera un JSON con la informacion de los games en la URL /api/games
  @RequestMapping("/games")
  public List<Object> getGameInfo() {
    return gRepository.findAll().stream().map(g -> g.toDTO()).collect(toList());
  }

  // Genera un JSON con la informacion de un game especifico en la URL /api/game_view/nn
  @RequestMapping("/game_view/{gamePlayerId}")
  public Map<String, Object> getGameView(@PathVariable long gamePlayerId) {
    GamePlayer gamePlayer = gpRepository.getOne(gamePlayerId);
    Map<String, Object> gameDTO = gamePlayer.getGame().toDTO();

    gameDTO.put("ships", gamePlayer.getShips()
        .stream()
        .map(s -> s.toDTO())
    );

    gameDTO.put("salvoes", gamePlayer.getGame().getGamePlayers()
        .stream()
        .map(gp -> gp.toSalvoDTO())
        .collect(toSet())
    );
    return gameDTO;
  }

}
