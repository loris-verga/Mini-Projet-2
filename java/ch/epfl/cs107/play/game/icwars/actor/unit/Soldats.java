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

    //camp du soldat
    private ICWarsTeamSide teamSide;
    //liste d'actions associées au soldat
    private ArrayList<ICWarsAction> listOfActions;


    /**
     * Constructeur de la classe soldat
     * @param areaOwner area associée au soldat
     * @param position position du soldat
     * @param teamSide camp du soldat
     */
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
        listOfActions.add(new Attack(areaOwner, this));
        listOfActions.add(new Wait(areaOwner, this));


    }

    /**
     * Méthode getName nécessaire à l'affichage du nom de l'unité
     * @return
     */
    public String getName(){
        return "Soldier";
    }

    /**
     * Méthode getDamage: retourne le nombre de dégâts que peut infliger l'unité à chaque coup
     * @return
     */
    @Override
    public float getDamage(){
        return 2.f;
    }

    /**
     * Retourne la liste d'action associée au soldat
     * @return
     */
    @Override
    public ArrayList<ICWarsAction> getListOfActions(){
        return listOfActions;
    }

}
