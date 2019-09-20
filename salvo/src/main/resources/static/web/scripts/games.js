$(function(){

  function showGameList(gameList){
    var li="";
    gameList.forEach(g => {
      var date = new Date(g.created).toLocaleString();
      var players = g.gamePlayers.map(gp => gp.player.email).join(', ');
      li+='<li>'+ date + ', ' + players + '</li>';
    });
    $('#gameList').html(li);
  }

  // Devuelve un JSON con la tabla de jugadores con puntajes mas altos
  function getLeaderTable(gameList){

    scoreList=[]; // Lista de names y scores
    var nameList=[];
    var leaderTable=[];
    gameList.forEach(game => {
      game.gamePlayers.forEach(gp => {
        scoreList.push(
          {
            name: gp.player.email,
            score: gp.score
          }
        );
      });
    });
    scoreList = scoreList.filter(player => player.score != null);

    // Lista de nombres unicos
    nameList = scoreList.map(s => s.name).
      filter((n,i,nl) => nl.indexOf(n)==i);

    nameList.forEach(name => {
      leaderTable.push(
        {
          name: name,
          total: getTotal(name).toFixed(1),
          won: getWon(name),
          lost: getLost(name),
          tied: getTied(name)
        }
      );
    });

    return leaderTable.sort((l1, l2) => l2.total - l1.total);
  }

  function getTotal(name){
    return scoreList.filter(s => s.name == name).reduce((total, s) => total + s.score, 0);
  }

  function getWon(name){
    return scoreList.filter(s => s.name == name && s.score == 1).length;
  }

  function getLost(name){
    return scoreList.filter(s => s.name == name && s.score == 0).length;
  }

  function getTied(name){
      return scoreList.filter(s => s.name == name && s.score == 0.5).length;
  }

  function showLeaderBoard(gameList){
    var table = "<tr>\
                  <th>Name</th><th>Total</th><th>Won</th><th>Lost</th><th>Tied</th>\
                 </tr>";
    getLeaderTable(gameList).forEach(player => {
      table += '<tr>\
                  <td>' + player.name + '</td>\
                  <td class="text-center">' + player.total + '</td>\
                  <td class="text-center">' + player.won + '</td>\
                  <td class="text-center">' + player.lost + '</td>\
                  <td class="text-center">' + player.tied + '</td>\
                </tr>';
    });

    $('#leaderBoard').html(table);
  }

  function loadData(){
      $.getJSON("/api/games")
      .done(function(data) {
          showGameList(data);
          showLeaderBoard(data);
      });
  }

  loadData();
});
