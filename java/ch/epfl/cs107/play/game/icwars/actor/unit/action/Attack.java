package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.icwars.actor.ICWarsActor;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.game.icwars.area.ICWarsArea;
import ch.epfl.cs107.play.game.icwars.area.ICWarsRange;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Attack extends ICWarsAction{

    ImageGraphics cursor;

    private int cible = 0;

    public Attack(Area area, Unit unit){
        super(area, unit);
        cursor = new ImageGraphics(ResourcePath.getSprite("icwars/UIpackSheet"),
                1f, 1f,
                new RegionOfInterest(4*18, 26*18,16,16));
    }

    @Override
    public void draw(Canvas canvas) {
        ArrayList<Integer> list = findIndexOfUnits();
        if (list == null) {}
        else{
            ICWarsArea area = (ICWarsArea)this.getArea();
            area.centerCameraOnUnit(cible);


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
        ICWarsActor.ICWarsTeamSide victimTeamSide;
        if (teamSide == ICWarsActor.ICWarsTeamSide.ALLY){
            victimTeamSide = ICWarsActor.ICWarsTeamSide.ENEMY;
        }
        else{victimTeamSide = ICWarsActor.ICWarsTeamSide.ALLY;}

        ICWarsArea area = (ICWarsArea) this.getArea();
        float x = this.getUnit().getPosition().x;
        float y = this.getUnit().getPosition().y;
        float radius = this.getUnit().getMovementRadius();
        listOfUnits = area.getListOfIndexOfUnitsInRange(x,y,radius, victimTeamSide);
        return listOfUnits;

    }

    @Override
    public  void doAction(float dt, ICWarsPlayer player , Keyboard keyboard) {

        ArrayList<Integer> listOfIndex = findIndexOfUnits();

        Button key;

        key = keyboard.get(Keyboard.RIGHT);
        if (key.isPressed()){
            cible = cible +1;
            if (cible > listOfIndex.size()-1){
                cible = 0;
            }
        }

        key = keyboard.get(Keyboard.LEFT);
        if (key.isPressed()){
            cible = cible -1;
            if (cible < 0){
                cible = listOfIndex.size()-1;
            }
        }

        key = keyboard.get(Keyboard.ENTER);
        if (key.isPressed()){

            Unit myUnit = this.getUnit();
            ICWarsArea area = (ICWarsArea) this.getArea();
            float damageOfUnit = myUnit.getDamage();
            float victimHp = area.getUnitHp(cible);
            int victimDefenseStars = area.getDefenseStarsUnit(cible);
            float damage;
            if (damageOfUnit-victimDefenseStars>0){
                damage = damageOfUnit;}
            else{damage = 0;}


            area.attackUnit(cible,damage);

            myUnit.becomeNotUsable();

            player.centerCamera();

        }
    }







}
