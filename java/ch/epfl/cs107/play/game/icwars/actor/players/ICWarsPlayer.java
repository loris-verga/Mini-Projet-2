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

/**
 * class abstraite ICWarsPlayer represente un joueur generique
 */

abstract class ICWarsPlayer extends ICWarsActor{

    public ArrayList<Unit> listOfUnits = new ArrayList<>();

    /**
     *  constructeur de la classe ICWarsPlayer
     * @param teamSide : la faction du joueur
     * @param area : l'aire ou se trouve le joueur
     * @param coordinates : les coordonner initial du joueur
     * @param units : les units qui appartient au joueur
     */

    ICWarsPlayer(ICWarsTeamSide teamSide, Area area, DiscreteCoordinates coordinates, Unit ... units){

        super(teamSide, area, coordinates);

        for (Unit unit: units){
              listOfUnits.add(unit);
              area.registerActor(unit);

        }
    }

    /**
     * methode update : enleve de l'aire et du tableau d'unites les unites qui sont mortes
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {
        ArrayList<Integer> listOfIndexToRemove= new ArrayList<>();

        for (int index=0; index < listOfUnits.size(); index++){
            Unit unit=listOfUnits.get(index);
            if (unit.isDead()){
                getOwnerArea().unregisterActor(unit);
                listOfIndexToRemove.add(index);
            }
        }
        for (int j=0; j < listOfIndexToRemove.size(); j++){
            listOfUnits.remove(listOfIndexToRemove.get(j));
        }

        super.update(deltaTime);
    }

    /**
     * methode isPlayerDefeated regarde si le joueur n'a plus d'unite
     * @return boolean
     */
    boolean isPlayerDefeated(){
        if (listOfUnits.isEmpty()){return true;}
        else {return false;}
    }


    /**
     * methode centerCamera permet de centrer la camera sur le joueur
     */
    void centerCamera(){getOwnerArea().setViewCandidate(this);}

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

}