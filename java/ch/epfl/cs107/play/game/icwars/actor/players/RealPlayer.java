package ch.epfl.cs107.play.game.icwars.actor.players;

import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.Unit;
import ch.epfl.cs107.play.game.icwars.gui.ICWarsPlayerGUI;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;
import java.awt.*;
import java.util.Collections;
import java.util.List;

/**
 * class RealPlayer represente le joueur reel, qui peut utiliser avec le clavier pour jouer
 */

public class RealPlayer extends ICWarsPlayer {

    private Sprite sprite;
    private String associatedImage;
    private final static int MOVE_DURATION = 8;


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

        Keyboard keyboard= getOwnerArea().getKeyboard();

        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));

        super.update(deltaTime);

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
        sprite.draw(canvas);
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
        return true;
    }
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public java.lang.String getName() {return "RealPlayer";}

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
    }
}
