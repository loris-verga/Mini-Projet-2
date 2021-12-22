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

    //attribut graphique associé au curseur afin de le dessiner
    ImageGraphics cursor;


    //Index de l'unité que l'on veut attaquer (réfère à la liste d'unité se trouvant dans Area)
    private int targetUnitIndex;

    //Cette liste contient la liste des index des unités qui sont de le camp adverse de l'unité sélectionnée.
    private ArrayList<Integer> listOfIndex;

    //Index de l'unité ennemie dans la liste d'index des unités ennemies.
    private int targetIndexInIndexList;

    /**
     * Constructeur de la classe Attack
     * @param area l'aire sur laquelle se déroule l'attaque
     * @param unit l'unité à laquelle l'action est attribuée
     */
    public Attack(Area area, Unit unit){
        super(area, unit);
        cursor = new ImageGraphics(ResourcePath.getSprite("icwars/UIpackSheet"), 1f, 1f, new RegionOfInterest(4*18, 26*18,16,16));
        cursor.setDepth(90.f);
    }


    /**
     * Méthode draw de l'action attaque :
     * Cette méthode va dessiner le curseur
     * @param canvas target, not null
     */
    @Override
    public void draw(Canvas canvas) {
        if (listOfIndex!=null && !(listOfIndex.isEmpty())) {

            ICWarsArea area = (ICWarsArea)this.getArea();
            area.centerCameraOnUnit(targetUnitIndex);

            cursor.setAnchor(canvas.getPosition().add(1,0));
            cursor.draw(canvas);
        }
    }


    /**
     * Méthode qui retourne le nom de l'action
     * @return
     */
    @Override
    public String getName(){
        return "(A)ttack";
    }

    /**
     * Méthode qui retourne la clé du clavier associée à l'action
     * @return
     */
    @Override
    public int getKey(){return Keyboard.A;}

    /**
     * Méthode qui retourne la liste des index des unités enemies.
     * @return
     */
    private ArrayList<Integer> getListOfIndexOfEnemyUnit(){
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

    /**
     * Méthode qui exécute l'action de l'unité
     * @param dt intervalle de temps
     * @param player joueur qui exécute l'action
     * @param keyboard le clavier sur lequel on va actionner les touches.
     */
    @Override
    public  void doAction(float dt, ICWarsPlayer player , Keyboard keyboard) {
        listOfIndex = getListOfIndexOfEnemyUnit();
        if (targetIndexInIndexList > listOfIndex.size()-1){targetIndexInIndexList=0;}

        Button key;
        key = keyboard.get(Keyboard.TAB);

        if (listOfIndex.size() == 0 || key.isPressed()) {
            player.centerCamera();
            player.setCurrentPlayerState(ICWarsPlayer.PlayerState.ACTION_SELECT);
        }
        else {
            targetUnitIndex = listOfIndex.get(targetIndexInIndexList);
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

                listOfIndex=null;
                myUnit.becomeNotUsable();
                player.centerCamera();
                player.setCurrentPlayerState(ICWarsPlayer.PlayerState.NORMAL);
            }
        }
    }


    //Fin de la classe Attack
}
