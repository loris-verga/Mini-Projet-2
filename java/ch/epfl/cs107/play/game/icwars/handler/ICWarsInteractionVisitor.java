package ch.epfl.cs107.play.game.icwars.handler;

import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;

/**
 * interface ICWarsInteractionVisitor permet de controler les interactions entre les interactors, les unit et les interactable
 */
public interface ICWarsInteractionVisitor extends AreaInteractionVisitor {
    default void interactWith(Unit unit){}
    default void interactWith(Interactable interactable){}


}
