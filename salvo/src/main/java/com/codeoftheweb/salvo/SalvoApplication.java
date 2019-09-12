package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.Entities.*;
import com.codeoftheweb.salvo.Repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SalvoApplication {

  public static void main(String[] args) {
    SpringApplication.run(SalvoApplication.class, args);
  }

  // Este codigo se ejecuta al inicio de la aplicacion
  @Bean
  public CommandLineRunner initData(PlayerRepository pRepository,
                                    GameRepository gRepository,
                                    GamePlayerRepository gpRepository,
                                    ShipRepository sRepository,
                                    SalvoRepository vRepository) {
    return (args) -> {

      // guardamos algunos players
      Player jack = new Player("j.bauer@ctu.gov");
      Player chloe = new Player("c.obrian@ctu.gov");
      Player kim = new Player("kim_bauer@gmail.com");
      Player tony = new Player("t.almeida@ctu.gov");
      pRepository.save(jack);
      pRepository.save(chloe);
      pRepository.save(kim);
      pRepository.save(tony);

      // guardamos algunos games
      Game game1 = new Game();
      Game game2 = new Game(1*3600);
      Game game3 = new Game(2*3600);
      Game game4 = new Game(3*3600);
      Game game5 = new Game(4*3600);
      Game game6 = new Game(5*3600);
      Game game7 = new Game(6*3600);
      Game game8 = new Game(7*3600);
      gRepository.save(game1);
      gRepository.save(game2);
      gRepository.save(game3);
      gRepository.save(game4);
      gRepository.save(game5);
      gRepository.save(game6);
      gRepository.save(game7);
      gRepository.save(game8);

      // guardamos algunos gamePlayers
      gpRepository.save(new GamePlayer(game1, jack));
      gpRepository.save(new GamePlayer(game1, chloe));
      gpRepository.save(new GamePlayer(game2, jack));
      gpRepository.save(new GamePlayer(game2, chloe));
      gpRepository.save(new GamePlayer(game3, chloe));
      gpRepository.save(new GamePlayer(game3, tony));
      gpRepository.save(new GamePlayer(game4, chloe));
      gpRepository.save(new GamePlayer(game4, jack));
      gpRepository.save(new GamePlayer(game5, tony));
      gpRepository.save(new GamePlayer(game5, jack));
      gpRepository.save(new GamePlayer(game6, kim));
      gpRepository.save(new GamePlayer(game7, tony));
      gpRepository.save(new GamePlayer(game8, kim));
      gpRepository.save(new GamePlayer(game8, tony));

      // guardamos algunas ships
      sRepository.save(new Ship("Destroyer", gpRepository.findByGameAndPlayer(game1, jack), new String[]{"H2", "H3", "H4"}));
      sRepository.save(new Ship("Submarine", gpRepository.findByGameAndPlayer(game1, jack), new String[]{"E1", "F1", "G1"}));
      sRepository.save(new Ship("Patrol Boat", gpRepository.findByGameAndPlayer(game1, jack), new String[]{"B4", "B5"}));
      sRepository.save(new Ship("Destroyer", gpRepository.findByGameAndPlayer(game1, chloe), new String[]{"B5", "C5", "D5"}));
      sRepository.save(new Ship("Patrol Boat", gpRepository.findByGameAndPlayer(game1, chloe), new String[]{"F1", "F2"}));
      sRepository.save(new Ship("Destroyer", gpRepository.findByGameAndPlayer(game2, jack), new String[]{"B5", "C5", "D5"}));
      sRepository.save(new Ship("Patrol Boat", gpRepository.findByGameAndPlayer(game2, jack), new String[]{"C6", "C7"}));
      sRepository.save(new Ship("Submarine", gpRepository.findByGameAndPlayer(game2, chloe), new String[]{"A2", "A3", "A4"}));
      sRepository.save(new Ship("Patrol Boat", gpRepository.findByGameAndPlayer(game2, chloe), new String[]{"G6", "H6"}));
      sRepository.save(new Ship("Destroyer", gpRepository.findByGameAndPlayer(game3, chloe), new String[]{"B5", "C5", "D5"}));
      sRepository.save(new Ship("Patrol Boat", gpRepository.findByGameAndPlayer(game3, chloe), new String[]{"C6", "C7"}));
      sRepository.save(new Ship("Submarine", gpRepository.findByGameAndPlayer(game3, tony), new String[]{"A2", "A3", "A4"}));
      sRepository.save(new Ship("Patrol Boat", gpRepository.findByGameAndPlayer(game3, tony), new String[]{"G6", "H6"}));
      sRepository.save(new Ship("Destroyer", gpRepository.findByGameAndPlayer(game4, chloe), new String[]{"B5", "C5", "D5"}));
      sRepository.save(new Ship("Patrol Boat", gpRepository.findByGameAndPlayer(game4, chloe), new String[]{"C6", "C7"}));
      sRepository.save(new Ship("Submarine", gpRepository.findByGameAndPlayer(game4, jack), new String[]{"A2", "A3", "A4"}));
      sRepository.save(new Ship("Patrol Boat", gpRepository.findByGameAndPlayer(game4, jack), new String[]{"G6", "H6"}));
      sRepository.save(new Ship("Destroyer", gpRepository.findByGameAndPlayer(game5, tony), new String[]{"B5", "C5", "D5"}));
      sRepository.save(new Ship("Patrol Boat", gpRepository.findByGameAndPlayer(game5, tony), new String[]{"C6", "C7"}));
      sRepository.save(new Ship("Submarine", gpRepository.findByGameAndPlayer(game5, jack), new String[]{"A2", "A3", "A4"}));
      sRepository.save(new Ship("Patrol Boat", gpRepository.findByGameAndPlayer(game5, jack), new String[]{"G6", "H6"}));
      sRepository.save(new Ship("Destroyer", gpRepository.findByGameAndPlayer(game6, kim), new String[]{"B5", "C5", "D5"}));
      sRepository.save(new Ship("Patrol Boat", gpRepository.findByGameAndPlayer(game6, kim), new String[]{"C6", "C7"}));
      sRepository.save(new Ship("Destroyer", gpRepository.findByGameAndPlayer(game8, kim), new String[]{"B5", "C5", "D5"}));
      sRepository.save(new Ship("Patrol Boat", gpRepository.findByGameAndPlayer(game8, kim), new String[]{"C6", "C7"}));
      sRepository.save(new Ship("Submarine", gpRepository.findByGameAndPlayer(game8, tony), new String[]{"A2", "A3", "A4"}));
      sRepository.save(new Ship("Patrol Boat", gpRepository.findByGameAndPlayer(game8, tony), new String[]{"G6", "H6"}));

      // guardamos algunos salvoes
      vRepository.save(new Salvo(gpRepository.findByGameAndPlayer(game1, jack), 1, new String[]{"B5", "C5", "F1"}));
      vRepository.save(new Salvo(gpRepository.findByGameAndPlayer(game1, chloe), 1, new String[]{"B4", "B5", "B6"}));
      vRepository.save(new Salvo(gpRepository.findByGameAndPlayer(game1, jack), 2, new String[]{"F2", "D5"}));
      vRepository.save(new Salvo(gpRepository.findByGameAndPlayer(game1, chloe), 2, new String[]{"E1", "H3", "A2"}));

      vRepository.save(new Salvo(gpRepository.findByGameAndPlayer(game2, jack), 1, new String[]{"A2", "A4", "G6"}));
      vRepository.save(new Salvo(gpRepository.findByGameAndPlayer(game2, chloe), 1, new String[]{"B5", "D5", "C7"}));
      vRepository.save(new Salvo(gpRepository.findByGameAndPlayer(game2, jack), 2, new String[]{"H3", "H6"}));
      vRepository.save(new Salvo(gpRepository.findByGameAndPlayer(game2, chloe), 2, new String[]{"C5", "C6"}));

      vRepository.save(new Salvo(gpRepository.findByGameAndPlayer(game3, chloe), 1, new String[]{"G6", "H6", "A4"}));
      vRepository.save(new Salvo(gpRepository.findByGameAndPlayer(game3, tony), 1, new String[]{"H1","H2","H3"}));
      vRepository.save(new Salvo(gpRepository.findByGameAndPlayer(game3, chloe), 2, new String[]{"A2", "A3", "D8"}));
      vRepository.save(new Salvo(gpRepository.findByGameAndPlayer(game3, tony), 2, new String[]{"E1","F2","G3"}));

      vRepository.save(new Salvo(gpRepository.findByGameAndPlayer(game4, chloe), 1, new String[]{"A3","A4","F7"}));
      vRepository.save(new Salvo(gpRepository.findByGameAndPlayer(game4, jack), 1, new String[]{"B5","C6","H1"}));
      vRepository.save(new Salvo(gpRepository.findByGameAndPlayer(game4, chloe), 2, new String[]{"A2","G6","H6"}));
      vRepository.save(new Salvo(gpRepository.findByGameAndPlayer(game4, jack), 2, new String[]{"C5","C7","D5"}));

      vRepository.save(new Salvo(gpRepository.findByGameAndPlayer(game5, tony), 1, new String[]{"A1","A2","A3"}));
      vRepository.save(new Salvo(gpRepository.findByGameAndPlayer(game5, jack), 1, new String[]{"B5","B6","C7"}));
      vRepository.save(new Salvo(gpRepository.findByGameAndPlayer(game5, tony), 2, new String[]{"G6","G7","G8"}));
      vRepository.save(new Salvo(gpRepository.findByGameAndPlayer(game5, jack), 2, new String[]{"C6","D6","E6"}));
      vRepository.save(new Salvo(gpRepository.findByGameAndPlayer(game5, jack), 3, new String[]{"H1","H8"}));

    };
  }
}
