package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public abstract class ICWarsActor extends MovableAreaEntity {

    //à ce niveau d'abstraction, on ne définit pas la façon dont les acteurs évoluent.

    //Variable qui stocke le camp d'un acteur
    ICWarsTeamSide teamSide;






    /**
     * Type énuméré qui définit le camp (allié ou ennemi) d'un acteur.
     * Je sais pas où le mettre /TODO
     */
    public enum ICWarsTeamSide {
        ALLY(0),
        ENEMY(1);


        final int teamIndex;

        ICWarsTeamSide(int teamIndex) {
            this.teamIndex = teamIndex;

        }
    }


    /**
     * Constructeur de ICWarsActor
     * @param teamSide représente le camp de l'acteur
     * @param owner représente l'aire sur laquelle l'acteur va s'initialiser
     * @param coordinates représente la position de l'acteur
     */
    public ICWarsActor(ICWarsTeamSide teamSide, Area owner, DiscreteCoordinates coordinates) {
        //On invoque le constructeur de la superclasse, on donne l'aire, la position et l'acteur est orienté vers le haut par défaut.
        super(owner, Orientation.UP, coordinates);
        this.teamSide = teamSide;



    }









    /**
     *Méthode enterArea: permet à un acteur d'entrer dans une aire.
     * @param area (Area): initial area, not null
     * @param position (DiscreteCoordinates): initial position, not null
     */
    public void enterArea(Area area, DiscreteCoordinates position){
        area.registerActor(this);
        setOwnerArea(area);
        setCurrentPosition(position.toVector());
    }

    /**Méthode pour que l'acteur quitte l'aire.
     *
     * Leave an area by unregister this player
     */
    public void leaveArea(){
        getOwnerArea().unregisterActor(this);
    }


    /**
     * Méthode qui permet de connaître les cellules qu'occupe l'acteur
     * @return retourne une liste de coordonnées de cellules.
     */
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    /**
     * Méthode getName
     * @return retourne le nom de l'acteur
     */
    public abstract String getName();










}










