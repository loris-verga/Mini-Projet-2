package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.lang.annotation.Documented;

public abstract class Unit extends ICWarsActor{


    //La valeur d'unitHp doit être positive et a une valeur maximale
    private float unitHp;
    private float unitHpMax;

    private Sprite sprite;

    //Le rayon de déplacement de l'unité (par tour)
    private int movementRadius;


    /**
     * Constructeur de la classe Unit
     * @param unitTeamSide
     * @param areaOwner
     * @param coordinates
     * @param unitHpMax
     * @param movementRadius
     */
    public Unit(ICWarsTeamSide unitTeamSide, Area areaOwner, DiscreteCoordinates coordinates, float unitHpMax, int movementRadius){

        super(unitTeamSide, areaOwner, coordinates);

        this.unitHpMax = unitHpMax;
        unitHp = unitHpMax;


        this.movementRadius = movementRadius;



    }

    /**
     * Constructeur du sprite
     * @param nameSprite
     */
    public void setSprite(String nameSprite){
        this.sprite = new Sprite(nameSprite, 1.5f, 1.5f, this, null, new Vector(-0.25f, -0.25f));
    }



    /**
     * Fonction repareHp: permet d'augmenter les points de vie d'une unité (on ne peut pas augmenter à plus d'unitHpMax)
     * @param hpToAdd : nombre de hp que l'on veut ajouter.
     */
    public void repareHp(float hpToAdd){
        unitHp = unitHp + hpToAdd;
        if (unitHp>unitHpMax){
            unitHp = unitHpMax;
        }
    }
    /**
     * Fonction sufferDamage : permet de faire subir des dommages à l'unité
     * @param hpToRemove : nombre de hp que l'on veut retirer. (on ne peut pas avoir de hp négatif)
     */
    public void sufferAmountOfDamage(float hpToRemove){
        unitHp = unitHp-hpToRemove;
        if (unitHp<0){
            unitHp=0.f;
        }
    }


    /**
     * Méthode getDamage: permet de pouvoir infliger un nombre fixe de dommage à chaque attaque.
     * On ne définit pas la méthode à ce niveau d'abstraction, car le nombre de dommages infligeable dépend de l'unité.
     * @return le nombre de dégats que l'on peut faire
     */
    public abstract float getDamage();


    /**
     * Fonction getName
     * @return retourne le nom de l'unité
     */

    public abstract String getName();

    /**
     * Fonction getHp
     * @return retourne la valeur des points de vie de l'unité
     */
    public float getHp(){
        return unitHp;
    }







    /**
     * Fonction isDead
     * @return retourne true si l'unité est morte.
     */
    public boolean isDead(){
        boolean dead = false;
        if (unitHp == 0.f){
            dead = true;
        }
        return dead;
    }


    /**
     * Une unité n'est pas traversable
     * @return true
     */
    @Override
    public boolean takeCellSpace(){return true;}

    /**
     * Une unité accepte les interactions de contact.
     * @return true
     */
    @Override
    public boolean isCellInteractable(){ return true;}

    /**
     * Une unité n'accepte pas les interactions à distance.
     * @return false
     */
    @Override
    public boolean isViewInteractable(){ return false;}



    @Override
    public void acceptInteraction(AreaInteractionVisitor v){
        //TODO
    }


    /**
     * méthode draw: permet de dessiner le sprite associé à l'unité
     * @param canvas target, not null
     */
    @Override
    public void draw(Canvas canvas){
        sprite.draw(canvas);
    }



}
