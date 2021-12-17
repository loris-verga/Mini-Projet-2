package ch.epfl.cs107.play.game.icwars.actor.unit;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Tanks extends Unit {

    //Un tank a un camp allié ou ennemi
    private ICWarsTeamSide teamSide;

    /**
     * Constructeur de la classe Tank
     * @param areaOwner l'aire à laquelle appartient le tank
     * @param position la position du tank
     * @param teamSide le camp du tank
     */
    public Tanks(Area areaOwner, DiscreteCoordinates position, ICWarsTeamSide teamSide){
        super(teamSide, areaOwner, position,10.f, 4);
        this.teamSide = teamSide;

        if (teamSide==ICWarsTeamSide.ALLY){
            //On initialise le sprite dans la classe Unit
            setSprite("icwars/friendlyTank");
        }
        else{
            setSprite("icwars/enemyTank");
        }




    }



    public String getName(){
        return this.teamSide.equals(ICWarsTeamSide.ALLY) ? "icwars/friendlyTank" : "icwars/enemyTank";
    }

    @Override
    public float getDamage(){
        return 7.f;
    }


}
