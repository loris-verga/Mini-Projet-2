package ch.epfl.cs107.play.game.icwars;


/**
 * Importation des packages
 */

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.window.Window;


/**
 * Classe ICWars qui contient le fonctionnement spécifique du jeu
 */

public class ICWars extends AreaGame{



    public final static float CAMERA_SCALE_FACTOR = 13.f;




    /**
     * Définition du camp allié ou ennemi
     */
    public enum ICWarsTeamSide{
        ALLY(0),
        ENEMY(1);


        final int teamIndex;

        ICWarsTeamSide(int teamIndex){
            this.teamIndex = teamIndex;

        }

    }


    /*
    /**
     * Add all the areas
     */
    private void createAreas(){

    }


    /**
     *
     * @param window : C'est la fenêtre sur laquelle le jeu va se dérouler (accès à un contexte graphique)
     * @param fileSystem C'est le système de fichier pour aller chercher des ressources
     * @return : La méthode retourne true si le démarrage du jeu s'est bien déroulé (la méthode begin a pu s'exécuter sans erreurs.
     */
  @Override
    public boolean begin(Window window, FileSystem fileSystem) {

        return true;
    }


    /**
     *
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

    }


    /** Méthode end
     *
     */
    @Override
    public void end() {
    }


    /**
     * Méthode getTitle:
     * @return
     */
    @Override
    public String getTitle() {
        return "ICWars";
    }


}
