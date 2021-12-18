package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.ArrayList;

public class Attack extends ICWarsAction{

    ImageGraphics cursor;

    private int targetIndexInIndexList;

    public Attack(Area area, Unit unit){
        super(area, unit);
        cursor = new ImageGraphics(ResourcePath.getSprite("icwars/UIpackSheet"), 1f, 1f, new RegionOfInterest(4*18, 26*18,16,16));
    }

    @Override
    public void draw(Canvas canvas) {
        ArrayList<Integer> list = findIndexOfUnits();
        if (list == null) {}
        else{
            ICWarsArea area = (ICWarsArea)this.getArea();
            area.centerCameraOnUnit(targetIndexInIndexList);


            cursor.setAnchor(canvas.getPosition().add(1,0));
            cursor.draw(canvas);


        }
    }




    @Override
    public String getName(){
        return "(A)ttack";
    }

    @Override
    public int getKey(){return Keyboard.A;}

    private ArrayList<Integer> findIndexOfUnits(){
        ArrayList <Integer> listOfUnits;
        ICWarsActor.ICWarsTeamSide teamSide = this.getUnit().getTeamSide();
        ICWarsActor.ICWarsTeamSide targetTeamSide;
        if (teamSide == ICWarsActor.ICWarsTeamSide.ALLY){
            targetTeamSide = ICWarsActor.ICWarsTeamSide.ENEMY;
        }
        else{targetTeamSide = ICWarsActor.ICWarsTeamSide.ALLY;}

        ICWarsArea area = (ICWarsArea) this.getArea();
        float x = this.getUnit().getPosition().x;
        float y = this.getUnit().getPosition().y;
        float radius = this.getUnit().getMovementRadius();
        listOfUnits = area.getListOfIndexOfUnitsInRange(x,y,radius, targetTeamSide);
        return listOfUnits;
    }

    @Override
    public  void doAction(float dt, ICWarsPlayer player , Keyboard keyboard) {
        ArrayList<Integer> listOfIndex = findIndexOfUnits();

        Button key;
        key = keyboard.get(Keyboard.TAB);

        if (listOfIndex.size() == 0 || key.isPressed()) {
            player.centerCamera();
            player.setCurrentState(ICWarsPlayer.PlayerState.ACTION_SELECT);
        }
        else {

            key = keyboard.get(Keyboard.RIGHT);
            if (key.isPressed()) {
                targetIndexInIndexList += 1;
                if (targetIndexInIndexList > listOfIndex.size() - 1) {
                    targetIndexInIndexList = 0;
                }
            }

            key = keyboard.get(Keyboard.LEFT);
            if (key.isPressed()) {
                targetIndexInIndexList -= 1;
                if (targetIndexInIndexList < 0) {
                    targetIndexInIndexList = listOfIndex.size() - 1;
                }
            }

            key = keyboard.get(Keyboard.ENTER);
            if (key.isPressed()) {

                Unit myUnit = this.getUnit();
                int targetUnitIndex = listOfIndex.get(targetIndexInIndexList);
                ICWarsArea area = (ICWarsArea) this.getArea();
                float damageOfUnit = myUnit.getDamage();
                int victimDefenseStars = area.getDefenseStarsUnit(targetUnitIndex);
                float damage;
                if (damageOfUnit - victimDefenseStars > 0) {
                    damage = damageOfUnit - victimDefenseStars;
                } else {
                    damage = 0;
                }

                area.attackUnit(targetUnitIndex, damage);

                myUnit.becomeNotUsable();
                player.centerCamera();
                player.setCurrentState(ICWarsPlayer.PlayerState.NORMAL);
            }

        }
    }







}
