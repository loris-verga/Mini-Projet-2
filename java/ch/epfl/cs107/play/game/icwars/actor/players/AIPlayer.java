package ch.epfl.cs107.play.game.icwars.actor.players;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Attack;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.ICWarsAction;
import ch.epfl.cs107.play.game.icwars.actor.unit.action.Wait;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.area.ICWarsBehavior;
import ch.epfl.cs107.play.game.icwars.handler.ICWarsInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * class AIPlayer represente le joueur virtuel
 */

public class AIPlayer extends ICWarsPlayer {

    private Sprite sprite;
    private String associatedImage;
    private final static int MOVE_DURATION = 8;
    private ICWarsAction action;

    private ICWarsBehavior.ICWarsCellType cellType;
    private ICWarsPlayerInteractionHandler handler;
    private int indexOfSelectedUnit;
    boolean counting = false;
    float counter = 0;
    /**
     * constructeur de AIPlayer
     * @param teamSide : faction du joueur
     * @param owner : aire que se trouve le joueur
     * @param coordinates : coordonner du joueur sur l'aire
     * @param units : les unites qui appartiennent au joueur
     */
    public AIPlayer(ICWarsTeamSide teamSide, Area owner, DiscreteCoordinates coordinates, Unit ... units) {
        super(teamSide ,owner, coordinates, units);

        if (teamSide.equals(ICWarsTeamSide.ALLY)){associatedImage="icwars/allyCursor";}
        else {associatedImage="icwars/enemyCursor";}

        sprite = new Sprite(associatedImage, 1.f, 1.f,this);

        handler = new ICWarsPlayerInteractionHandler();

        indexOfSelectedUnit=0;
    }

    /**
     * Ensures that value time elapsed before returning true
     * @param dt elapsed time
     * @param value waiting time (in seconds)
     * @return true if value seconds has elapsed , false otherwise
     */
    private boolean waitFor(float value , float dt) {

        if (counting) {
            counter += dt;
            if (counter > value) {
                counting = false;
                return true;
            }
        } else {
            counter = 0f;
            counting = true;
        }
        return false;
    }

    /**
     * methode centerCamera permet de centrer la camera sur le joueur
     */
    public void centerCamera() {
        getOwnerArea().setViewCandidate(this);
    }

    /**
     *  methode update permet de detecter les movements du joueur avec le clavier
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {

        super.update(deltaTime);

        updateState(deltaTime);
    }

    private boolean stillHaveUsableUnits(){
        boolean test = false;
            for(Unit unit : listOfUnits){
                if (unit.getMarkAsUsed()==false){test=true;}
            }
        return test;
    }

    /**
     * methode updateState permet de mettre a jour l'etat du joueur dependant des actions du joueur
     */
    private void updateState(float deltaTime){
        switch (getCurrentPlayerState()){

            case IDLE: {break;}

            case NORMAL:{
                ICWarsArea area =(ICWarsArea)getOwnerArea();
                if (listOfUnits.isEmpty() || stillHaveUsableUnits()==false || area.enemyDefeated(this.getTeamSide())==true) {
                    indexOfSelectedUnit=0;
                    if(waitFor(0.5f, deltaTime)){
                        setCurrentPlayerState(PlayerState.IDLE);}
                    break;
                }
                else{
                    setCurrentPlayerState(PlayerState.SELECT_CELL);
                    break;
                }

            }

            case SELECT_CELL: {
                if (indexOfSelectedUnit<listOfUnits.size()) {
                    selectUnit(indexOfSelectedUnit);
                    if (selectedUnit.getMarkAsUsed()==true){
                        indexOfSelectedUnit+=1;
                        selectUnit(indexOfSelectedUnit);
                    }
                    if(waitFor(0.5f, deltaTime)){
                        setCurrentPlayerState(PlayerState.MOVE_UNIT);}
                }
                else{onLeaving(getCurrentCells());}
                break;
            }
            case MOVE_UNIT: {
                ICWarsArea area =(ICWarsArea)getOwnerArea();
                DiscreteCoordinates targetCoordinates = area.findClosestTarget(selectedUnit.getPosition().x, selectedUnit.getPosition().y, selectedUnit.getTeamSide());
                DiscreteCoordinates myUnitNewCoordinates = area.FindMoveClosestToTarget(selectedUnit , selectedUnit.getPosition().x, selectedUnit.getPosition().y, selectedUnit.getMovementRadius(), targetCoordinates.x, targetCoordinates.y );
                if(waitFor(0.5f, deltaTime)){
                    if (selectedUnit.changePosition(myUnitNewCoordinates)) {
                        this.changePosition(new DiscreteCoordinates((int)selectedUnit.getPosition().x, (int)selectedUnit.getPosition().y));
                        setCurrentPlayerState(PlayerState.ACTION_SELECT);
                    }
                }
                break;
            }

            case ACTION_SELECT:{
                ICWarsActor.ICWarsTeamSide targetTeamSide;
                if (selectedUnit.getTeamSide() == ICWarsActor.ICWarsTeamSide.ALLY){targetTeamSide = ICWarsActor.ICWarsTeamSide.ENEMY;}
                else {targetTeamSide = ICWarsActor.ICWarsTeamSide.ALLY;}
                ICWarsArea area =(ICWarsArea)getOwnerArea();
                float newX = selectedUnit.getPosition().x;
                float newY = selectedUnit.getPosition().y;
                float range = selectedUnit.getMovementRadius();
                ArrayList<Integer> listOfIndexOfUnitsInRange = area.getListOfIndexOfUnitsInRange(newX , newY , range ,targetTeamSide);
                if (listOfIndexOfUnitsInRange.isEmpty()){
                    action = new Wait(area , selectedUnit);
                    if(waitFor(0.5f, deltaTime)){
                        setCurrentPlayerState(PlayerState.ACTION);}
                }
                else{
                    action = new Attack(area , selectedUnit);
                    if(waitFor(0.5f, deltaTime)){
                        setCurrentPlayerState(PlayerState.ACTION);}
                }
                break;
            }
            case ACTION:{
                action.doAutoAction(9, this );
                break;
            }
        }
    }


    /**
     * methode leaveArea permet de desenregistrer des acteurs de l'aire
     */
    public void leaveArea(){
        getOwnerArea().unregisterActor(this);
    }

    /**
     * methode enterArea permet de registrer le joueur et ses unites dans l'aire
     * @param area (Area): aire initial, pas nulle
     * @param position (DiscreteCoordinates): position original , pas nulle
     */
    public void enterArea(Area area, DiscreteCoordinates position){
        area.registerActor(this);
        area.setViewCandidate(this);
        setOwnerArea(area);
        setCurrentPosition(position.toVector());
        resetMotion();
    }

    @Override
    public void draw(Canvas canvas) {
        if (getCurrentPlayerState()!=PlayerState.IDLE){
            sprite.draw(canvas);
        }
        playerGUI.draw(canvas);
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public java.lang.String getName() {return "AIPlayer";}

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {}

    @Override
    public void interactWith(Interactable v){
        if (!isDisplacementOccurs()) {
            v.acceptInteraction(handler);
        }
    }

    /**
     * class imbriquer ICWarsPlayerInteractionHandler prendre en charge les interactions entre les joueurs et les unites
     */
    private class ICWarsPlayerInteractionHandler implements ICWarsInteractionVisitor {
        public void interactWith(ICWarsBehavior.ICWarsCell cell){
            if (getCurrentPlayerState()==PlayerState.NORMAL || getCurrentPlayerState()==PlayerState.SELECT_CELL) {
                cellType = cell.getCellType();
                playerGUI.setCellInfoPanel(cellType);
            }
        }

        public void interactWith(Unit unit) {
            if (getCurrentPlayerState()==PlayerState.SELECT_CELL || getCurrentPlayerState()==PlayerState.NORMAL){
                playerGUI.setUnitInfoPanel(unit);
            }
            //if (getCurrentPlayerState()==PlayerState.SELECT_CELL && unit.getTeamSide().equals(getTeamSide()) && unit.markAsUsed==false){
            //    for (int i = 0; i < listOfUnits.size(); ++i) {
            //        if (unit == listOfUnits.get(i)) {
             //         selectUnit(i);
            //            break;
            //        }
            //    }
            //}
        }
    }
}
