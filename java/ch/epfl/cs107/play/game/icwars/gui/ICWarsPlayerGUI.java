package ch.epfl.cs107.play.game.icwars.gui;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.icwars.actor.players.RealPlayer;
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
        icWarsInfoPanel = new ICWarsInfoPanel(cameraScaleFactor);
        icWarsActionPanel = new ICWarsActionsPanel(cameraScaleFactor);

    }


    public void setSelectedUnit(Unit unit){
        selectedUnit = unit;
    }




    @Override
    public void draw(Canvas canvas) {
        RealPlayer player1 = (RealPlayer) player;

        if (!(selectedUnit == null)) {
            selectedUnit.drawRangeAndPathTo(player.getCurrentCells().get(0), canvas);
            if (player.currentState == ICWarsPlayer.PlayerState.ACTION_SELECT) {
                icWarsActionPanel.setActions(selectedUnit.getListOfActions());
                icWarsActionPanel.draw(canvas);
            }
        }
        if (player.currentState == ICWarsPlayer.PlayerState.NORMAL || player.currentState == ICWarsPlayer.PlayerState.SELECT_CELL){
            icWarsInfoPanel.setCurrentCell(player1.getPlayerCellType());
            icWarsInfoPanel.setUnit(selectedUnit);
            icWarsInfoPanel.draw(canvas);
        }


    }
}
