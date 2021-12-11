package ch.epfl.cs107.play.game.icwars.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Path;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icwars.area.ICWarsRange;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.lang.annotation.Documented;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Queue;

public abstract class Unit extends ICWarsActor{

    private ICWarsRange range;




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

//Nœuds de positions
        int heightArea = areaOwner.getHeight();
        int widthArea = areaOwner.getWidth();
        this.range = new ICWarsRange();
        initRange(coordinates.x, coordinates.y, movementRadius, heightArea, widthArea);





    }

    /**
     * Cette méthode appelle les méthodes qui servent à initialiser le range.
     * @param coordX
     * @param coordY
     * @param radius
     * @param height
     * @param width
     */
    public void initRange(int coordX, int coordY, int radius, int height, int width){
        ArrayList<int[]> nodesCoords = getNodesCoords(coordX, coordY, radius, height, width);
        addAllNodes(nodesCoords, coordX, coordY, radius);
    }


    /**
     * Cette méthode retourne une liste de coordonnées de nœuds, c'est-à-dire la liste des coordonnées des points qui se situent
     * à une certaine distance (radius) du point (fromX,fromY)
     * @param fromX
     * @param fromY
     * @param radius
     * @param maxX dimensions de la fenêtre
     * @param maxY
     * @return une array qui contient des tableaux qui contiennent les coordonnées x et y des tableaux
     */
    public ArrayList<int[]> getNodesCoords(int fromX,int  fromY, int radius, int maxX, int maxY){
        ArrayList<int[]> list = new ArrayList<int[]>();
        for (int coordX = 0; coordX<maxX; ++ coordX){
            for (int coordY = 0; coordY<maxY; ++ coordY){
                if ((coordX<=fromX+radius)&&(coordX>=fromX-radius)&&(coordY<=fromY+radius)&&(coordY>=fromY-radius)){
                    list.add(new int[]{coordX, coordY});
                }
            }
        }
        return list;
    }

    /**
     * Méthode qui ajoute les nœuds
     * @param nodesCoords
     * @param fromX
     * @param fromY
     * @param radius
     */
    public void addAllNodes(ArrayList<int[]> nodesCoords, int fromX, int fromY, int radius){
        for (int indexArray = 0; indexArray < nodesCoords.size(); ++indexArray){
            int coordX = nodesCoords.get(indexArray)[0];
            int coordY = nodesCoords.get(indexArray)[1];
            boolean hasLeftEdge = false;
            boolean hasUpEdge = false;
            boolean hasRightEdge = false;
            boolean hasDownEdge = false;
            //à vérifier /TODO
            //ce que dit l'énoncé: if (coordX >-radius && coordX + fromX>0){hasLeftEdge = true;}
            //ma reflexion:
            if (coordX>fromX-radius){hasLeftEdge = true;}
            if (coordX<fromX+radius){hasRightEdge = true;}
            if(coordY>fromY-radius){hasDownEdge = true;}
            if(coordY<fromY+radius){hasUpEdge = true;}

            range.addNode(new DiscreteCoordinates(coordX, coordY), hasLeftEdge, hasUpEdge, hasRightEdge, hasDownEdge);

        }
    }
/*
    public void testGetNodesCoords(){
        Area currentArea = getOwnerArea();
        int height = currentArea.getHeight();
        int width = currentArea.getWidth();
        ArrayList<int[]> test = getNodesCoords(4, 4, 3, height, width);
        System.out.print(test);



    }
*/
        //getCurrentCells()
        //https://piazza.com/class/ktijhp746sr283?cid=642





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



    /**
     * Draw the unit's range and a path from the unit position to
     destination
     * @param destination path destination
     * @param canvas canvas
     */
    public void drawRangeAndPathTo(DiscreteCoordinates destination ,
                                   Canvas canvas) {
        range.draw(canvas);
        Queue<Orientation> path =
                range.shortestPath(getCurrentMainCellCoordinates(),destination);

        //Draw path only if it exists (destination inside the range)
        if (path != null){
            new Path(getCurrentMainCellCoordinates().toVector(),
                    path).draw(canvas);
        }
    }




}
