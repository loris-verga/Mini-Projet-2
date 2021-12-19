package ch.epfl.cs107.play.game.icwars.actor.unit;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Attack;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.ICWarsAction;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Wait;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;
import java.util.List;

public class Soldats extends Unit {

    private ICWarsTeamSide teamSide;

    private ArrayList<ICWarsAction> listOfActions;

    private Attack attack;
    private Wait wait;

    public Soldats(Area areaOwner, DiscreteCoordinates position, ICWarsTeamSide teamSide){
        super(teamSide, areaOwner, position,5.f, 2);
        this.teamSide = teamSide;

        if (teamSide==ICWarsTeamSide.ALLY){
            setSprite("icwars/friendlySoldier");
        }
        else{
            setSprite("icwars/enemySoldier");
        }

        listOfActions = new ArrayList<>();
        attack = new Attack(areaOwner, this);
        listOfActions.add(attack);
        wait = new Wait(areaOwner, this);
        listOfActions.add(wait);


    }


    public String getName(){
        return this.teamSide.equals(ICWarsTeamSide.ALLY) ? "icwars/friendlySoldier" : "icwars/enemySoldier";
    }

    @Override
    public float getDamage(){
        return 2.f;
    }

    @Override
    public ArrayList<ICWarsAction> getListOfActions(){
        return listOfActions;
    }

}
