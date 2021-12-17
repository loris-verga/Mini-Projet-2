package ch.epfl.cs107.play.game.icwars.actor.players;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.gui.ICWarsPlayerGUI;
import ch.epfl.cs107.play.game.icwars.handler.ICWarsInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * class abstraite ICWarsPlayer represente un joueur generique
 */

public abstract class ICWarsPlayer extends ICWarsActor implements Interactor{

    public ArrayList<Unit> listOfUnits = new ArrayList<>();

    protected ICWarsPlayerGUI playerGUI;

    protected Unit selectedUnit;
    //todo mettre en private si possible
    public PlayerState currentState;

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

        playerGUI = new ICWarsPlayerGUI(0.f, this);

        selectedUnit = null;

        currentState = PlayerState.IDLE;
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return null;
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        return false;
    }

    @Override
    public void interactWith(Interactable other) {}

    /**
     * enumerer PlayerState corresponds aux differents etats que le joueur peut prendre
     */
    public enum PlayerState{
        IDLE,
        NORMAL,
        SELECT_CELL,
        MOVE_UNIT,
        ACTION_SELECT,
        ACTION;

    }

    /**
     * methode startTurn permet de commencer le tour du joueur, lui donner ces unites et centrer la camera sur lui
     */
    public void startTurn(){
        currentState = PlayerState.NORMAL;
        centerCamera();

        for (Unit unit: listOfUnits){unit.becomeUsable();}
    }

    /**
     * Called when this Interactable leaves a cell
     * @param coordinates left cell coordinates
     */
    @Override
    public void onLeaving(List<DiscreteCoordinates> coordinates){
        if (currentState == PlayerState.SELECT_CELL){currentState = PlayerState.NORMAL;}
    }

    @Override
    public boolean changePosition(DiscreteCoordinates newPosition) {
        return super.changePosition(newPosition);
    }

    /**
     * methode update : enleve de l'aire et du tableau d'unites les unites qui sont mortes
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {
        ArrayList<Integer> listOfIndexToRemove= new ArrayList<>();

        //todo not sure
        for (Unit unit : listOfUnits){
            if (unit.isDead()){
                getOwnerArea().unregisterActor(unit);
                listOfIndexToRemove.add(listOfUnits.indexOf(unit));
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
    public boolean isPlayerDefeated(){
        if (listOfUnits.isEmpty()){return true;}
        else {return false;}
    }

    /**
     * methode centerCamera permet de centrer la camera sur le joueur
     */
    public void centerCamera(){getOwnerArea().setViewCandidate(this);}

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

    @Override
    public void acceptInteraction(AreaInteractionVisitor v){
        ((ICWarsInteractionVisitor)v).interactWith(this);
    }

    /**
     * Méthode selectUnit: permet de sélectionner une unité.
     */
    public void selectUnit(int indice) {
        if (indice < listOfUnits.size() && indice >= 0) {
            selectedUnit = listOfUnits.get(indice);
            playerGUI.setSelectedUnit(selectedUnit);

        }
    }


    public void setCurrentState(PlayerState currentState) {
        this.currentState = currentState;
    }
}