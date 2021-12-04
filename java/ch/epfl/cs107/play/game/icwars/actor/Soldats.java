package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Soldats extends Unit{

    private ICWarsTeamSide teamSide;


    public Soldats(Area areaOwner, DiscreteCoordinates position, ICWarsTeamSide teamSide){
        super(teamSide, areaOwner, position,5.f, 2);
        this.teamSide = teamSide;

        if (teamSide==ICWarsTeamSide.ALLY){
            setSprite("icwars/friendlySoldier");
        }
        else{
            setSprite("icwars/enemySoldier");
        }




    }


    public String getName(){
        return this.teamSide.equals(ICWarsTeamSide.ALLY) ? "icwars/friendlySoldier" : "icwars/enemySoldier";
    }

    @Override
    public float getDamage(){
        return 2.f;
    }


}
