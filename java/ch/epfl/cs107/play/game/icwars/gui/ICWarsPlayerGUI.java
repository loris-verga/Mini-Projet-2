package ch.epfl.cs107.play.game.icwars.gui;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.window.Canvas;

public class ICWarsPlayerGUI implements Graphics {

    ICWarsPlayer player;
    Unit selectedUnit;
    public final static float FONT_SIZE = 20.f;
    private ICWarsActionsPanel icWarsActionPanel;
    private ICWarsInfoPanel icWarsInfoPanel;

    public ICWarsPlayerGUI(float cameraScaleFactor, ICWarsPlayer player){
        this.player = player;

    }


    public void setSelectedUnit(Unit unit){
        selectedUnit = unit;
    }




    @Override
    public void draw(Canvas canvas) {
        //if (!(selectedUnit == null)) {
        //    selectedUnit.drawRangeAndPathTo(player.getCurrentCells().get(0), canvas);
        //}
        if (player.currentState == ICWarsPlayer.PlayerState.ACTION_SELECT){
            icWarsActionPanel.setActions(selectedUnit.getListOfActions());
            icWarsActionPanel.draw(canvas);
        }
        if (player.currentState == ICWarsPlayer.PlayerState.NORMAL || player.currentState == ICWarsPlayer.PlayerState.SELECT_CELL){
            //todo not sure
            if (!(selectedUnit == null)) {
                selectedUnit.drawRangeAndPathTo(player.getCurrentCells().get(0), canvas);


            }
            icWarsInfoPanel.draw(canvas);
        }


    }
}
