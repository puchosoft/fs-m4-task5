$(function(){

  columns = ['1','2','3','4','5','6','7','8','9','10'];
  rows = ['A','B','C','D','E','F','G','H','I','J'];
  you_ship_visibility = Array();
  you_salvo_visibility = Array();

  // Inicializa la visibilidad de ships y salvoes en null
  for(i=0;i<10;i++){
    you_ship_visibility.push([null,null,null,null,null,null,null,null,null,null]);
    you_salvo_visibility.push([null,null,null,null,null,null,null,null,null,null]);
  }

  loadData();

});

function loadData(){
  if(location.search.startsWith("?gp=")){
    id = location.search.slice(4);
    $.getJSON("/api/game_view/"+id)
          .done(function(data) {
            showPlayersInfo(id,data.gamePlayers);
            showGrids(data.ships, data.salvoes);
          });
  }
}

// Obtiene los IDs de ambos Players
function getPlayersInfo(id, gamePlayers){
  index=(gamePlayers[0].id != id);
  youID = gamePlayers[Number(index)].player.id;
  youEmail = gamePlayers[Number(index)].player.email;
  enemyID = 0;
  enemyEmail = '-nobody-';
  if(gamePlayers.length > 1){
    enemyID = gamePlayers[Number(!index)].player.id;
    enemyEmail = gamePlayers[Number(!index)].player.email;
  }
}

//Muestra la informacion de los Players
function showPlayersInfo(id, gamePlayers){
  getPlayersInfo(id, gamePlayers);
  $('#you-info').html('&nbsp You: &nbsp<i>'+ youEmail +' </i>&nbsp');
  $('#enemy-info').html('&nbsp Enemy: &nbsp<i>'+ enemyEmail +' </i>&nbsp');
}

// Marca con (0) las ubicaciones de los ships en la grilla
function setShipsVisibility(ships){
  ships.forEach(ship => {
    ship.locations.forEach(loc => {
      row = loc.slice(0,1).charCodeAt(0) - 'A'.charCodeAt(0);
      col = loc.slice(1)-1;
      you_ship_visibility[row][col] = 0;
    });
  });
}

// Marca con (nº turno) las ubicaciones de los salvos en la grilla
function setSalvoesVisibility(salvoes){
  salvoes.forEach(turn => {
    turn.shots.forEach(shot => {
      row = shot.slice(0,1).charCodeAt(0) - 'A'.charCodeAt(0);
      col = shot.slice(1)-1;
      you_salvo_visibility[row][col] = turn.turn;
    });
  });
}

function setShipsDamage(enemySalvoes){
  enemySalvoes.forEach(t => {
    t.shots.forEach(s => {
      row = s.slice(0,1).charCodeAt(0) - 'A'.charCodeAt(0);
      col = s.slice(1)-1;
      if(you_ship_visibility[row][col]==0){
        you_ship_visibility[row][col] = t.turn;
      }
    });
  });
}

// Muestra los headers de las columnas de la grilla
function showColumnHeaders(){
  var tr ="<tr class='bg-dark'><th class='py-1'></th>";
  columns.forEach(c => {
    tr+="<th class='py-1 text-white'>" + c + "</th>";
  });
  tr+="</tr>";
  $('#ship-headers').html(tr);
  $('#salvo-headers').html(tr);
}

// Muestra las filas de las grillas
function showRows(){
  shipTR = "";
  salvoTR = "";
  rows.forEach((r, rix) => {
    shipTR+= "<tr><th class='py-1 text-white bg-dark'>" + r + "</th>";
    salvoTR+= "<tr><th class='py-1 text-white bg-dark'>" + r + "</th>";
    columns.forEach((c, cix) => {
      var ship_visibility = (you_ship_visibility[rix][cix] != null?'block':'none');
      if(you_ship_visibility[rix][cix] > 0){
        ship_damage = 'danger';
        ship_text = you_ship_visibility[rix][cix];
      }
      else {
        ship_damage = 'light';
        ship_text = '';
      }

      shipTR+= '\
        <td class="p-0 w-2">\
          <div class="py-2 badge badge-'+ ship_damage +' border border-dark d-'+ ship_visibility +'">\
            &nbsp'+ ship_text +'&nbsp\
          </div></td>';

      var salvo_visibility = (you_salvo_visibility[rix][cix]!=null?'block':'none');
      salvoTR+= '\
              <td class="p-0 w-2">\
                <div class="py-2 badge badge-pill badge-warning border border-dark d-'+ salvo_visibility +'">\
                  &nbsp'+ you_salvo_visibility[rix][cix] +'&nbsp\
                </div></td>';
    });
    shipTR+= "</tr>";
    salvoTR+= "</tr>";
  });
  $('#ship-rows').html(shipTR);
  $('#salvo-rows').html(salvoTR);
}

function showGrids(ships, salvoes){
  setShipsVisibility(ships);
  if(enemyID > 0){
    setShipsDamage(salvoes.filter( p => p.playerID == enemyID)[0].turns);
  }
  setSalvoesVisibility(salvoes.filter( p => p.playerID == youID)[0].turns)
  showColumnHeaders();
  showRows();
}
