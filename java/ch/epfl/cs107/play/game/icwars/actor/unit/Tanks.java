package ch.epfl.cs107.play.game.icwars.actor.unit;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Attack;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.ICWarsAction;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Wait;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;
import java.util.List;

public class Tanks extends Unit {

    //Un tank a un camp allié ou ennemi
    private ICWarsTeamSide teamSide;

    //Liste d'action associée au tank
    private ArrayList <ICWarsAction> listOfActions;


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

        listOfActions = new ArrayList<>();
        listOfActions.add(new Attack(areaOwner, this));
        listOfActions.add(new Wait(areaOwner, this));

    }


    /**
     * Méthode getName : sert à afficher le nom de l'unité
     * @return
     */
    public String getName(){
        return "Tank";
    }

    /**
     * Méthode getDamage : retourne le nombre de dégâts que peut infliger l'unité à chaque coup
     * @return
     */
    @Override
    public float getDamage(){
        return 7.f;
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
