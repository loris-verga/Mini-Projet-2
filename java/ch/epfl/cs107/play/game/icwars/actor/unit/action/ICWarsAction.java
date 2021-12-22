package ch.epfl.cs107.play.game.icwars.actor.unit.action;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.icwars.actor.players.ICWarsPlayer;
import ch.epfl.cs107.play.game.icwars.actor.unit.Unit;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public abstract class ICWarsAction implements Graphics {

    //Une action est associée à une unité
    private Unit unit;

    //Une action est associée à une aire
    private Area area;


    /**
     * Constructeur de la classe action
     * @param area aire sur laquelle se trouve l'unité qui possède l'action
     * @param unit unité à laquelle est rattachée l'action
     */
    public ICWarsAction(Area area, Unit unit){
        this.unit = unit;
        this.area = area;
    }

    /**
     * Méthode protected (accès uniquement dans le package action) qui retourne l'aire associée à l'action
     * @return
     */
    protected Area getArea(){return area;}

    /**
     * Méthode protected (accès uniquement dans le package action) qui retourne l'unité associée à l'action
     * @return
     */
    protected Unit getUnit(){return unit;}

    /**
     * Méthode abstraite qui doit renvoyer le nom de l'action
     * @return
     */
    public abstract String getName();

    /**
     * Méthode abstraite qui renvoie la touche du clavier associée à l'action
     * @return
     */
    public abstract int getKey();

    /**
     * Méthode doAction qui exécute l'action de l'attaque
     * @param dt intervalle de temps
     * @param player joueur associé à l'action
     * @param keyboard clavier pour saisir les touches associées aux actions
     */
    public abstract void doAction(float dt, ICWarsPlayer player , Keyboard keyboard);


}
