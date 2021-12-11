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


/**
 * Classe ICWars qui contient le fonctionnement spécifique du jeu
 */

public class ICWars extends AreaGame{

    public final static float CAMERA_SCALE_FACTOR = 13.f;
    public final static float STEP = 0.05f;

    private final String[] areas = {"icwars/Level0", "icwars/Level1"};
    private static int areaIndex;

    RealPlayer realPlayer;

    /**
     * methode createAreas initialise les nouvelles aires
     */
    private void createAreas(){
        addArea(new Level0());
        addArea(new Level1());
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

        Keyboard keyboard = getWindow().getKeyboard() ;

        Button key = keyboard.get(Keyboard.N) ;
        if (key.isPressed()) {nextArea();}

        key = keyboard.get(Keyboard.R) ;
        if (key.isPressed()) {
            areaIndex=0;
            initArea();
        }

        if (keyboard.get(Keyboard.U).isReleased()) {
            ((RealPlayer)realPlayer).selectUnit (0); // 0, 1 ...
        }

    }

    /**
     * methode nextArea permet de passer a la prochaine aire dans le tableau areas
     */

    private void nextArea(){
        areaIndex+=1;
        realPlayer.leaveArea();
        if (areaIndex <= areas.length - 1 ){initArea();}
        else{end();}
    }

    /**
     * methode initArea permet d'initialiser l'aire qu'on se trouve avec les joueurs et ces units
     */

    private void initArea(){
        Area area = setCurrentArea(areas[areaIndex], true);

        ICWarsActor.ICWarsTeamSide teamSideRealPlayer= ICWarsActor.ICWarsTeamSide.ALLY;

        Unit tank1 = new Tanks( area , new DiscreteCoordinates(2,5), teamSideRealPlayer);
        Unit soldier1 = new Soldats( area, new DiscreteCoordinates(3,5), teamSideRealPlayer);

        int[][] PlayerCoordinatesForArea={{ 0 , 0 } , { 2 , 5 } };
        realPlayer = new RealPlayer(teamSideRealPlayer , area , new DiscreteCoordinates(0, 0), soldier1, tank1);
        realPlayer.enterArea(area, new DiscreteCoordinates(PlayerCoordinatesForArea[areaIndex][0],PlayerCoordinatesForArea[areaIndex][1]));
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
