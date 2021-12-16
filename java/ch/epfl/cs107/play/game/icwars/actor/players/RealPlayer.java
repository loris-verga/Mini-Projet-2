package ch.epfl.cs107.play.game.icwars.actor.players;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.ICWars;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.handler.ICWarsInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.Collections;
import java.util.List;

/**
 * class RealPlayer represente le joueur reel, qui peut utiliser avec le clavier pour jouer
 */

public class RealPlayer extends ICWarsPlayer {

    private Sprite sprite;
    private String associatedImage;
    private final static int MOVE_DURATION = 8;

    ICWarsPlayerInteractionHandler handler;
    /**
     * constructeur de RealPlayer
     * @param teamSide : faction du joueur
     * @param owner : aire que se trouve le joueur
     * @param coordinates : coordonner du joueur sur l'aire
     * @param units : les unites qui appartiennent au joueur
     */
    public RealPlayer(ICWarsTeamSide teamSide, Area owner, DiscreteCoordinates coordinates, Unit ... units) {
        super(teamSide ,owner, coordinates, units);

        if (teamSide.equals(ICWarsTeamSide.ALLY)){associatedImage="icwars/allyCursor";}
        else {associatedImage="icwars/enemyCursor";}

        sprite = new Sprite(associatedImage, 1.f, 1.f,this);

        handler = new ICWarsPlayerInteractionHandler();
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

        updateState();
    }

    /**
     * methode getcurrentState permet de voir l'etat courant du joueur
     * @return currentState l'etat current du joueur
     */
    public PlayerState getcurrentState(){return currentState;}


    /**
     * methode changecurrentState permet de changer l'etat courant du joueur
     */
    public void changecurrentState(PlayerState NewState){currentState = NewState;}

    /**
     * methode canMove donne la possibilite au joueur de bouger
     */
    private void canMove(){
        Keyboard keyboard = getOwnerArea().getKeyboard();
        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));
    }

    /**
     * methode moveIfPressed detect les touches du clavier pour orienter et faire bouger le joueur dans la bonne direction
     * @param orientation (Orientation): given orientation, not null
     * @param b (Button): button corresponding to the given orientation, not null
     */
    private void moveIfPressed(Orientation orientation, ch.epfl.cs107.play.window.Button b){
        if(b.isDown()) {
            if (!isDisplacementOccurs()) {
                orientate(orientation);
                move(MOVE_DURATION);
            }
        }
    }

    /**
     * methode updateState permet de mettre a jour l'etat du joueur dependant des actions du joueur
     */
    private void updateState(){
        Keyboard keyboard = getOwnerArea().getKeyboard() ;
        Button key;

       switch (currentState){

           case IDLE: {break;}

           case NORMAL:{
               canMove();
               key = keyboard.get(Keyboard.ENTER) ;
               if (key.isPressed()){
                   changecurrentState(PlayerState.SELECT_CELL);
                   break;
               }
               key = keyboard.get(Keyboard.TAB);
               if (key.isPressed()){
                   changecurrentState(PlayerState.IDLE);
               }
           }

           case SELECT_CELL: {
               canMove();

               if (selectedUnit!=null) {changecurrentState(PlayerState.MOVE_UNIT);}
               else{onLeaving(getCurrentCells());}
               break;
           }
           case MOVE_UNIT: {
               canMove();
               key = keyboard.get(Keyboard.ENTER);

               if (key.isPressed() && changePosition(getCurrentMainCellCoordinates()) && selectedUnit.changePosition(getCurrentMainCellCoordinates())){
                   selectedUnit.becomeNotUsable();
                   selectedUnit = null;
                   changecurrentState(PlayerState.NORMAL);
               }
               break;
           }
           case ACTION_SELECT:{break;}
               //todo later
           case ACTION:{break;}
               //todo later
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
        if (getcurrentState()!=PlayerState.IDLE){
            sprite.draw(canvas);
        }
        if (getcurrentState()==PlayerState.MOVE_UNIT) {
            playerGUI.draw(canvas);
        }
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
    public java.lang.String getName() {return "RealPlayer";}

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {}

    @Override
    public void interactWith(Interactable v){
        if (isDisplacementOccurs()==false) {
            v.acceptInteraction(handler);
        }
    }

    /**
     * class imbriquer ICWarsPlayerInteractionHandler prendre en charge les interactions entre les joueurs et les unites
     */
    private class ICWarsPlayerInteractionHandler implements ICWarsInteractionVisitor {


        public void interactWith(Unit unit) {
            //todo not sure about this
            if (currentState==PlayerState.SELECT_CELL && unit.getTeamSide().equals(getTeamSide()) && unit.markAsUsed==false){
                for (int i = 0; i < listOfUnits.size(); ++i) {
                    if (unit == listOfUnits.get(i)) {
                        selectUnit(i);
                        break;
                    }
                }
            }
        }
    }
}
