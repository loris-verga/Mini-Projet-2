package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.ICWars;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;


public abstract class ICWarsArea extends Area {

    //Behavior associé à l'aire, gère le comportement de l'aire
    private ICWarsBehavior behavior;

    //Liste des unités de l'aire
    private ArrayList<Unit> listOfUnitsInTheArea = new ArrayList<Unit>();

    /**
     * Permet d'ajouter des unités à la liste d'unité de l'aire
     * @param unit
     */
    public void addUnitArea(Unit unit){
        listOfUnitsInTheArea.add(unit);
    }

    /**
     * Permet d'enlever des unités à la liste d'unité de l'aire
     * @param unit
     */
    public void removeUnitArea(Unit unit){listOfUnitsInTheArea.remove(unit);}

    /**
     * Retourne une liste d'unité qui se trouvent dans un rayon autour d'une position
     * @param fromX
     * @param fromY
     * @param radius
     * @param victimTeamSide
     * @return
     */
    public ArrayList <Integer> getListOfIndexOfUnitsInRange(float fromX, float fromY, float radius, ICWarsActor.ICWarsTeamSide victimTeamSide){
        ArrayList <Integer> listOfIndex = new ArrayList<>();
        for (int i = 0; i<listOfUnitsInTheArea.size(); ++i){
            Unit unit = listOfUnitsInTheArea.get(i);
            if (unit.getTeamSide().equals(victimTeamSide)){
                float x = unit.getPosition().x;
                float y = unit.getPosition().y;
                if ((fromX-radius<=x)&&(x<=fromX+radius)&&(fromY-radius<y)&&(y<fromY+radius)){
                    int index = listOfUnitsInTheArea.indexOf(unit);
                    listOfIndex.add(index);
                }

            }
        }
        return listOfIndex;
    }


    /**
     * Create the area by adding it all actors
     * called by begin method
     * Note it set the Behavior as needed !
     */
    protected abstract void createArea();

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            // Set the behavior map
            behavior = new ICWarsBehavior(window, getTitle());
            setBehavior(behavior);
            createArea();
            return true;
        }
        return false;
    }

    /**
     * Méthode getCameraScaleFactor: Toutes les areas de notre jeu ont un CameraScaleFactor qui définit la taille
     * du champ de vision.
     * @return le facteur de champ de vision dont la valeur finale est définie dans la classe du jeu ICWars.
     */
    @Override
    public final float getCameraScaleFactor() {
        return ICWars.CAMERA_SCALE_FACTOR;
    }


    /**
     * Retourne le nombre d'étoiles de défense de la cellule sur laquelle se trouve une unité
     * @param indexOfUnit
     * @return
     */
    public int getDefenseStarsUnit(int indexOfUnit){
        Unit unit = listOfUnitsInTheArea.get(indexOfUnit);
        return unit.getDefenseStarsOnCell();
    }

    /**
     * Permet d'infliger des dégâts à une unité
     * @param indexOfUnit
     * @param damage
     */
    public void attackUnit(int indexOfUnit, float damage){
        Unit unit = listOfUnitsInTheArea.get(indexOfUnit);
        unit.sufferAmountOfDamage(damage);
    }

    /**
     * Permet de centrer la caméra sur une unité
     * @param indexOfUnit
     */
    public void centerCameraOnUnit(int indexOfUnit){
        this.setViewCandidate(listOfUnitsInTheArea.get(indexOfUnit));
    }



}
