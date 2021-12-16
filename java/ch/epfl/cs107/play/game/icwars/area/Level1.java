package ch.epfl.cs107.play.game.icwars.area;


import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;

/**
 * C'est une seconde aire spécifique de notre jeu.
 */
public class Level1 extends ICWarsArea{

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
        return "icwars/Level1";
    }


}
