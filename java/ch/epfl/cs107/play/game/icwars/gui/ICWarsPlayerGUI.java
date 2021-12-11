package ch.epfl.cs107.play.game.icwars.gui;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class ICWarsPlayerGUI implements Graphics {

    ICWarsPlayer player;
    Unit selectedUnit;

    public ICWarsPlayerGUI(float cameraScaleFactor, ICWarsPlayer player){
        this.player = player;

    }


    public void setSelectedUnit(Unit unit){
        selectedUnit = unit;
    }




    @Override
    public void draw(Canvas canvas) {
        if (!(selectedUnit == null)) {
            selectedUnit.drawRangeAndPathTo(player.getCurrentCells().get(0), canvas);
        }







    }
}
