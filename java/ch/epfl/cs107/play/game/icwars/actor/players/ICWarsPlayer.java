package ch.epfl.cs107.play.game.icwars.actor.players;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.ArrayList;

//todo commentary

abstract class ICWarsPlayer extends ICWarsActor{

    public ArrayList<Unit> listOfUnits = new ArrayList<>();

    /**
     *  constructor de la classe ICWarsPlayer
     * @param teamSide
     * @param area
     * @param coordinates
     * @param units
     */

    ICWarsPlayer(ICWarsTeamSide teamSide, Area area, DiscreteCoordinates coordinates, Unit ... units){

        super(teamSide, area, coordinates);

        for (Unit unit: units){
              listOfUnits.add(unit);
              area.registerActor(unit);

        }
        //todo check if this is correct and didn`t forget anything...
    }

    @Override
    public void update(float deltaTime) {
        ArrayList<Integer> listOfIndexToRemove= new ArrayList<Integer>();

        for (int index=0; index < listOfUnits.size(); index++){
            Unit unit=listOfUnits.get(index);
            if (unit.isDead()){
                getOwnerArea().unregisterActor(unit);
                listOfIndexToRemove.add(index);
            }
        }
        for (int j=0; j < listOfIndexToRemove.size(); j++){listOfUnits.remove(listOfIndexToRemove.get(j));}

        //todo check if using an array is correct and if it can be shorter
        super.update(deltaTime);

    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return false;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    void centerCamera(){getOwnerArea().setViewCandidate(this);}

    boolean isPlayerDefeated(){
        if (listOfUnits.isEmpty()){return true;}
        else {return false;}
    }


}