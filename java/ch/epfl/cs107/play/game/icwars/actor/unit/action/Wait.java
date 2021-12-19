package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class Wait extends ICWarsAction{


    public Wait(Area area, Unit unit){
        super(area,unit);
    }


    @Override
    public String getName(){
        return "(W)ait";
    }

    @Override
    public int getKey(){
        return Keyboard.W;
    }

    @Override
    public void draw(Canvas canvas){
        //do nothing
    }

    public void doAction(float dt, ICWarsPlayer player , Keyboard keyboard){
        this.getUnit().becomeNotUsable();
        player.setCurrentPlayerState(ICWarsPlayer.PlayerState.NORMAL);
    }


}
