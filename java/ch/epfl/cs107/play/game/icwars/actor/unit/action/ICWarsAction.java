package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public abstract class ICWarsAction implements Graphics {

    private Unit unit;
    private Area area;



    public ICWarsAction(Area area, Unit unit){
        this.unit = unit;
        this.area = area;
    }

    protected Area getArea(){return area;}

    protected Unit getUnit(){return unit;}

    public abstract String getName();

    public abstract int getKey();

    public abstract void doAction(float dt, ICWarsPlayer player , Keyboard keyboard);


}
