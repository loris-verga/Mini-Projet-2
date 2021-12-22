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

    private ICWarsBehavior behavior;

    private ArrayList<Unit> listOfUnitsInTheArea = new ArrayList<Unit>();

    public void addUnitArea(Unit unit){listOfUnitsInTheArea.add(unit);}

    public void removeUnitArea(Unit unit){listOfUnitsInTheArea.remove(unit);}

    public boolean enemyDefeated(ICWarsActor.ICWarsTeamSide teamSide){
        boolean test = true;
            for(Unit unit : listOfUnitsInTheArea){
                if (unit.getTeamSide() != teamSide){test = false;}
            }
        return test;
    }

    public boolean isPositionTaken(DiscreteCoordinates coordinates){
        for (int i = 0; i<listOfUnitsInTheArea.size(); ++i){
            Unit unit = listOfUnitsInTheArea.get(i);
            if (unit.getCurrentCells().get(0).equals(coordinates)){
                return true;
            }
        }
        return false;
    }

    public int findIndexOfLowestHpUnit(ArrayList<Integer> listOfIndex){
        float minHpValue = listOfUnitsInTheArea.get(listOfIndex.get(0)).getHp();
        Unit unitWithLowestHp = listOfUnitsInTheArea.get(listOfIndex.get(0));
        for (Integer indexInIndexList :listOfIndex){
            float hpOfUnit = listOfUnitsInTheArea.get(listOfIndex.get(indexInIndexList)).getHp();
            if (hpOfUnit < minHpValue){
                minHpValue = hpOfUnit;
                unitWithLowestHp = listOfUnitsInTheArea.get(listOfIndex.get(indexInIndexList));
            }
        }
    return listOfUnitsInTheArea.indexOf(unitWithLowestHp);
    }

    public DiscreteCoordinates findClosestTarget(float coordX, float coordY, ICWarsActor.ICWarsTeamSide myUnitTeamSide){
        ICWarsActor.ICWarsTeamSide targetTeamSide;
        if (myUnitTeamSide == ICWarsActor.ICWarsTeamSide.ALLY){targetTeamSide = ICWarsActor.ICWarsTeamSide.ENEMY;}
        else {targetTeamSide = ICWarsActor.ICWarsTeamSide.ALLY;}
        float minDistance;
        int ClosestTargetIndex;
        ArrayList <Unit> listOfPossibleTarget = new ArrayList<>();
        for (Unit possibleTarget: listOfUnitsInTheArea){
            if (possibleTarget.getTeamSide().equals(targetTeamSide)){
                listOfPossibleTarget.add(possibleTarget);
            }
        }

        ClosestTargetIndex = listOfUnitsInTheArea.indexOf(listOfPossibleTarget.get(0));
        float x = listOfPossibleTarget.get(0).getPosition().x;
        float y = listOfPossibleTarget.get(0).getPosition().y;
        minDistance = (float) Math.sqrt(Math.pow(x-coordX,2) + Math.pow(y-coordY , 2));
        for (int i = 1 ; i < listOfPossibleTarget.size() ; i++){
            Unit possibleClosestTarget = listOfPossibleTarget.get(i);
            x = possibleClosestTarget.getPosition().x;
            y = possibleClosestTarget.getPosition().y;
            float distance = (float) Math.sqrt(Math.pow(x-coordX,2) + Math.pow(y-coordY , 2));
            if (minDistance > distance ){
                minDistance = distance;
                ClosestTargetIndex = listOfUnitsInTheArea.indexOf(possibleClosestTarget);
            }
        }
        listOfPossibleTarget.clear();
        int closestX = (int) listOfUnitsInTheArea.get(ClosestTargetIndex).getPosition().x;
        int closestY = (int) listOfUnitsInTheArea.get(ClosestTargetIndex).getPosition().y;
        return new DiscreteCoordinates(closestX, closestY);
    }


    public DiscreteCoordinates FindMoveClosestToTarget(Unit selectedUnit ,float myUnitX, float myUnitY, float myUnitRange, float targetX, float targetY){
        int startingRangeX = (int) (myUnitX - myUnitRange);
        int startingRangeY = (int) (myUnitY - myUnitRange);
        int endingRangeX = (int) (myUnitX + myUnitRange);
        int endingRangeY = (int) (myUnitY + myUnitRange);
        int minDistance = this.getWidth();
        DiscreteCoordinates bestPosition = null;
        if (startingRangeX < 0){startingRangeX=0;}
        if (startingRangeX < 0){startingRangeY=0;}
        if (endingRangeX >= this.getWidth()){endingRangeX = this.getWidth();}
        if (endingRangeY >= this.getHeight()){endingRangeY = this.getHeight();}
        for(int i = startingRangeX; i < endingRangeX; i++){
            for(int j = startingRangeY ; j < endingRangeY; j++){
                float distance = (float) Math.sqrt(Math.pow(targetX - i ,2) + Math.pow(targetY - j, 2));
                //todo add valid conditions
                if ((minDistance > distance && !this.isPositionTaken(new DiscreteCoordinates(i,j)))){
                    minDistance = (int) distance;
                    bestPosition = new DiscreteCoordinates(i , j);
                }
            }
        }
        return bestPosition;
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



    public float getUnitHp(int indexOfUnit){
        Unit unit = listOfUnitsInTheArea.get(indexOfUnit);
        return unit.getHp();
    }

    public int getDefenseStarsUnit(int indexOfUnit){
        Unit unit = listOfUnitsInTheArea.get(indexOfUnit);
        return unit.getDefenseStarsOnCell();
    }

    public void attackUnit(int indexOfUnit, float damage){
        Unit unit = listOfUnitsInTheArea.get(indexOfUnit);
        unit.sufferAmountOfDamage(damage);
    }


    public void centerCameraOnUnit(int indexOfUnit){
        this.setViewCandidate(listOfUnitsInTheArea.get(indexOfUnit));
    }

}
