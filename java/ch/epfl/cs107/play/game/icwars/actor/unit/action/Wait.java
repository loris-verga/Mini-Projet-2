package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class Wait extends ICWarsAction{

    /**
     * Constructeur de la classe Wait
     * @param area aire associée à l'action
     * @param unit unité associée à l'action
     */
    public Wait(Area area, Unit unit){
        super(area,unit);
    }


    /**
     * Méthode getName: renvoie le nom de l'unité
     * @return
     */
    @Override
    public String getName(){
        return "(W)ait";
    }

    /**
     * Méthode getKey retourne la touche de clavier associée à l'action wait
     * @return
     */
    @Override
    public int getKey(){
        return Keyboard.W;
    }

    /**
     * Méthode draw, ne fais rien
     * @param canvas target, not null
     */
    @Override
    public void draw(Canvas canvas){
        //do nothing
    }

    /**
     * Méthode doAction: fait en sorte que l'unité ne soit plus utilisable durant ce tour.
     * @param dt intervalle de temps
     * @param player joueur associé à l'action
     * @param keyboard clavier pour saisir les touches associées à l'action wait
     */
    public void doAction(float dt, ICWarsPlayer player , Keyboard keyboard){
        this.getUnit().becomeNotUsable();
        player.setCurrentPlayerState(ICWarsPlayer.PlayerState.NORMAL);
    }


}
