package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.ICWars;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;


public abstract class ICWarsArea extends Area {

    private ICWarsBehavior behavior;

    private ArrayList<Unit> listOfUnitsInTheArea = new ArrayList<Unit>();

    public void addUnitArea(Unit unit){
        listOfUnitsInTheArea.add(unit);
    }

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

}
