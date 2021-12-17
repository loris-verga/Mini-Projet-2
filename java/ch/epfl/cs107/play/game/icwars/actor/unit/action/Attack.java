package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.area.ICWarsRange;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.ArrayList;

public class Attack extends ICWarsAction{

    private int indexOfSelectionedUnit;

    public Attack(Area area, Unit unit){
        super(area, unit);
    }



    @Override
    public String getName(){
        return "(A)ttack";
    }

    @Override
    public int getKey(){return Keyboard.A;}

    private ArrayList<Integer> findIndexOfUnits(){
        ArrayList <Integer> listOfUnits;
        ICWarsActor.ICWarsTeamSide teamSide = this.getUnit().getTeamSide();
        ICWarsActor.ICWarsTeamSide victimTeamSide;
        if (teamSide == ICWarsActor.ICWarsTeamSide.ALLY){
            victimTeamSide = ICWarsActor.ICWarsTeamSide.ENEMY;
        }
        else{victimTeamSide = ICWarsActor.ICWarsTeamSide.ALLY;}

        ICWarsArea area = (ICWarsArea) this.getArea();
        float x = this.getUnit().getPosition().x;
        float y = this.getUnit().getPosition().y;
        float radius = this.getUnit().getMovementRadius();
        listOfUnits = area.getListOfIndexOfUnitsInRange(x,y,radius, victimTeamSide);
        return listOfUnits;

    }






}
