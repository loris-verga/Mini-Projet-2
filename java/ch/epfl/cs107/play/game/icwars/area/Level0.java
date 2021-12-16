package ch.epfl.cs107.play.game.icwars.area;


import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;

/**
 * C'est une aire spécifique de notre jeu.
 */
public class Level0 extends ICWarsArea{

    /**
     * methode createArea permet de creer l'aire pour le premier niveau
     */
    protected void createArea() {
        // Base
        registerActor(new Background(this));
        //registerActor(new Foreground(this));
    }

    /**
     * Redéfinition de la méthode getTitle
     * @return : Retourne le nom de l'aire. C'est important pour trouver le bon fichier associé.
     */
    @Override
    public String getTitle(){
        return "icwars/Level0";
    }


}
