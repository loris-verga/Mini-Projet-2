package ch.epfl.cs107.play.game.icwars.gui;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.area.ICWarsBehavior;
import ch.epfl.cs107.play.window.Canvas;

public class ICWarsPlayerGUI implements Graphics {

    ICWarsPlayer player;
    Unit selectedUnit;
    public final static float FONT_SIZE = 20.f;
    private ICWarsActionsPanel icWarsActionPanel;
    private ICWarsInfoPanel icWarsInfoPanel;

    /**
     * Constructeur de la classe
     * @param cameraScaleFactor
     * @param player
     */
    public ICWarsPlayerGUI(float cameraScaleFactor, ICWarsPlayer player){
        this.player = player;
        icWarsInfoPanel = new ICWarsInfoPanel(13.f);
        icWarsActionPanel = new ICWarsActionsPanel(13.f);

    }

    /**
     * Sélectionne l'unit
     * @param unit
     */
    public void setSelectedUnit(Unit unit){
        selectedUnit = unit;
    }

    /**
     * met l'unité dans info panel
     * @param unit
     */
    public void setUnitInfoPanel(Unit unit){icWarsInfoPanel.setUnit(unit);}

    /**
     * met le type de cellule dans l'info panel
     * @param cellType
     */
    public void setCellInfoPanel(ICWarsBehavior.ICWarsCellType cellType){icWarsInfoPanel.setCurrentCell(cellType);}

    @Override
    public void draw(Canvas canvas) {
        switch (player.getCurrentPlayerState()){
            case NORMAL:{}
            case SELECT_CELL:{
                icWarsInfoPanel.draw(canvas);
                break;
            }
            case MOVE_UNIT:{
                selectedUnit.drawRangeAndPathTo(player.getCurrentCells().get(0), canvas);
                break;
            }
            case ACTION_SELECT:{
                icWarsActionPanel.setActions(selectedUnit.getListOfActions());
                icWarsActionPanel.draw(canvas);
                break;
            }
        }
        setUnitInfoPanel(null);
        setCellInfoPanel(null);
    }
}
