package ch.epfl.cs107.play.game.icwars;


/**
 * Importation des packages
 */

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.Soldats;
import ch.epfl.cs107.play.game.icwars.actor.Tanks;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;
import ch.epfl.cs107.play.game.icwars.area.Level0;
import ch.epfl.cs107.play.game.icwars.area.Level1;
import ch.epfl.cs107.play.game.tutosSolution.actor.SimpleGhost;
import ch.epfl.cs107.play.game.tutosSolution.area.tuto1.Ferme;
import ch.epfl.cs107.play.game.tutosSolution.area.tuto1.Village;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;


/**
 * Classe ICWars qui contient le fonctionnement spécifique du jeu
 */

public class ICWars extends AreaGame{

    public final static float CAMERA_SCALE_FACTOR = 13.f;
    public final static float STEP = 0.05f;

    private final String[] areas = {"icwars/Level0", "icwars/Level1"};
    private static int areaIndex;
    //Todo remove this is uneciry
    //private static int currentPlayerIndex;
    private static ICWarsPlayer currentPlayer;
    private static ArrayList<ICWarsPlayer> listOfCurrentWaitingPlayer = new ArrayList<>();
    private static ArrayList<ICWarsPlayer> listOfFuturWaitingPlayer = new ArrayList<>();
    private GameState gameState;

    ICWarsPlayer Player1;
    ICWarsPlayer Player2;
    ArrayList<ICWarsPlayer> listOfPlayers = new ArrayList<>();

    /**
     * methode createAreas initialise les nouvelles aires
     */
    private void createAreas(){
        addArea(new Level0());
        addArea(new Level1());
    }

    private enum GameState{
        INIT,
        CHOOSE_PLAYER,
        START_PLAYER_TURN,
        PLAYER_TURN,
        END_PLAYER_TURN,
        END_TURN,
        END;
    }

    /**
     *
     * @param window : C'est la fenêtre sur laquelle le jeu va se dérouler (accès à un contexte graphique)
     * @param fileSystem C'est le système de fichier pour aller chercher des ressources
     * @return : La méthode retourne true si le démarrage du jeu s'est bien déroulé (la méthode begin a pu s'exécuter sans erreurs.
     */

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
      if (super.begin(window, fileSystem)) {
          createAreas();
          areaIndex = 0;
          initArea();
          return true;
      }
      return false;
  }


    /**
     * methode update voit si le joueur veux changer de niveau ou si il veut recommencer le jeu
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        updateGameState();

        /*for  (ICWarsPlayer player : listOfPlayers){
            if (player.isPlayerDefeated()){
                //todo have to check this once attack method is finished...
                listOfPlayers.remove(player);
                listOfCurrentWaitingPlayer.remove(player);
                listOfFuturWaitingPlayer.remove(player);
            }
        }*/

        Keyboard keyboard = getWindow().getKeyboard() ;

        Button key = keyboard.get(Keyboard.N) ;
        if (key.isPressed()) {nextArea();}

        key = keyboard.get(Keyboard.R) ;
        if (key.isPressed()) {
            areaIndex=0;
            initArea();
        }

    }

    private void updateGameState(){
        switch (gameState) {
            case INIT :{
                for (ICWarsPlayer player : listOfPlayers){listOfCurrentWaitingPlayer.add(player);}
                switchGameState(GameState.CHOOSE_PLAYER);
                break;
            }
            case CHOOSE_PLAYER:{
                if (listOfCurrentWaitingPlayer.isEmpty()){
                    switchGameState(GameState.END_TURN);
                }
                else{
                    currentPlayer = listOfCurrentWaitingPlayer.get(0);
                    switchGameState(GameState.START_PLAYER_TURN);
                }
                break;
            }
            case START_PLAYER_TURN:{
                currentPlayer.startTurn();
                switchGameState(GameState.PLAYER_TURN);
                break;
            }
            case PLAYER_TURN:{
                if (currentPlayer.currentState.equals(ICWarsPlayer.PlayerState.IDLE)){switchGameState(GameState.END_PLAYER_TURN);}
                break;
            }
            case END_PLAYER_TURN:{
                //todo verify not sure
                if (currentPlayer.isPlayerDefeated()){
                    currentPlayer.leaveArea();
                }
                else{
                    listOfCurrentWaitingPlayer.remove(currentPlayer);
                    listOfFuturWaitingPlayer.add(currentPlayer);
                }
                switchGameState(GameState.CHOOSE_PLAYER);
                break;
            }
            case END_TURN:{
                //todo remove all defeated players
                if (listOfFuturWaitingPlayer.size()==1){switchGameState(gameState.END);}
                else{
                    for(ICWarsPlayer player : listOfFuturWaitingPlayer){listOfCurrentWaitingPlayer.add(player);}
                    listOfFuturWaitingPlayer.clear();
                    switchGameState(GameState.CHOOSE_PLAYER);
                }
                break;
            }
            case END:{
                listOfFuturWaitingPlayer.clear();
                listOfCurrentWaitingPlayer.clear();
                nextArea();
                break;
            }
        }
    }

    private void switchGameState(GameState newGameState){gameState=newGameState;}

    /**
     * methode nextArea permet de passer a la prochaine aire dans le tableau areas
     */

    private void nextArea(){
        areaIndex+=1;
        for  (ICWarsPlayer player : listOfPlayers){player.leaveArea();}

        if (areaIndex <= areas.length - 1 ){initArea();}
        else{end();}
    }

    /**
     * methode initArea permet d'initialiser l'aire qu'on se trouve avec les joueurs et ces units
     */

    private void initArea(){
        //todo make this shorter if it works
        Area area = setCurrentArea(areas[areaIndex], true);

        ICWarsActor.ICWarsTeamSide teamSidePlayer1= ICWarsActor.ICWarsTeamSide.ALLY;
        ICWarsActor.ICWarsTeamSide teamSidePlayer2= ICWarsActor.ICWarsTeamSide.ENEMY;

        Unit tank1 = new Tanks( area , new DiscreteCoordinates(2,5), teamSidePlayer1);
        Unit soldier1 = new Soldats( area, new DiscreteCoordinates(3,5), teamSidePlayer1);

        Unit tank2 = new Tanks( area , new DiscreteCoordinates(8,5), teamSidePlayer2);
        Unit soldier2 = new Soldats( area, new DiscreteCoordinates(9,5), teamSidePlayer2);

        int[][] Player1CoordinatesForArea={{ 0 , 0 } , { 2 , 5 } };
        Player1 = new RealPlayer(teamSidePlayer1 , area , new DiscreteCoordinates(Player1CoordinatesForArea[areaIndex][0],Player1CoordinatesForArea[areaIndex][1]), soldier1, tank1);
        Player1.enterArea(area, new DiscreteCoordinates(Player1CoordinatesForArea[areaIndex][0],Player1CoordinatesForArea[areaIndex][1]));

        int[][] Player2CoordinatesForArea={{ 7 , 4 } , { 17 , 5 }};
        Player2 = new RealPlayer(teamSidePlayer2 , area , new DiscreteCoordinates(Player2CoordinatesForArea[areaIndex][0],Player2CoordinatesForArea[areaIndex][1]), soldier2, tank2);
        Player2.enterArea(area, new DiscreteCoordinates(Player2CoordinatesForArea[areaIndex][0],Player2CoordinatesForArea[areaIndex][1]));

        listOfPlayers.add(Player1);
        listOfPlayers.add(Player2);
        switchGameState(GameState.INIT);
    }

    /**
     * methode end permet de terminer le jeu
     */
    @Override
    public void end() {
        System.out.println("GAME OVER");
        //todo close window??
    }


    /**
     * methode publique getTitle permet de trouver le titre de la classe
     * @return
     */
    @Override
    public String getTitle() {
        return "ICWars";
    }


}
