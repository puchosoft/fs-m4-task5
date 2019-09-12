package com.codeoftheweb.salvo.Repositories;

import com.codeoftheweb.salvo.Entities.Game;
import com.codeoftheweb.salvo.Entities.GamePlayer;
import com.codeoftheweb.salvo.Entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface GamePlayerRepository extends JpaRepository<GamePlayer, Long> {

  public GamePlayer findByGameAndPlayer(Game game, Player player);

}
