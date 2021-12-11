package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.window.Window;


/**
 * Classe ICWarsBehavior :
 * Cette classe sert à gérer le comportement d'une area.
 */
public class ICWarsBehavior extends AreaBehavior {


    /**
     * L'énumération ICWarsCellType définit le type des cellules d'une area.
     */
    public enum ICWarsCellType {

        NONE(0, 0), // Should never be used except
        // in the toType method
        ROAD(-16777216, 0), // the second value is the number
        // of defense stars
        PLAIN(-14112955, 1),
        WOOD(-65536, 3),
        RIVER(-16776961, 0),
        MOUNTAIN(-256, 4),
        CITY(-1, 2);

        /**
         * La variable type représente le type de cellule. Par exemple : de l'herbe, des rochers, de l'eau, ...
         * C'est un nombre int qui est associé à chaque type de cellule
         */
        final int type;


        /**
         * La variable defenseStars représente le nombre d'étoiles de défense qu'a une cellule. Ce nombre d'étoiles représente
         * le taux de protection de la cellule. Si une cellule a un nombre d'étoiles faible, il sera facile d'y accéder.
         */
        final int defenseStars;


        ICWarsCellType(int type, int defenseStars) {
            this.type = type;
            this.defenseStars = defenseStars;
        }

        /**
         * Cette méthode retourne le nom associé au nombre int de l'énumération. (Par exemple, -1 retourne CITY)
         *
         * @param type
         * @return Nom associé
         */
        public static ICWarsCellType toType(int type) {
            for (ICWarsCellType valeur : ICWarsCellType.values()) {
                if (valeur.type == type) {
                    return valeur;
                }
            }
            //Lorsqu'on ajoute une nouvelle couleur, on peut la print avant de l'assigner à un type.
            System.out.println(type);
            return NONE;
        }
    }
    //Fin de l'énumération


    /**
     * Constructeur par défaut de la classe ICWarsBehavior
     * @param window correspond à la fenêtre sur laquelle va s'afficher le jeu
     * @param name correspond au nom du fichier de l'image behavior où l'on va trouver le type des cases
     */
    public ICWarsBehavior(Window window, String name){
        //On appelle le constructeur de la classe AreaBehavior
        super(window, name);
        //On récupère les dimensions de la fenêtre
        int height = getHeight();
        int width = getWidth();
        //On associe le type aux cellules
        for (int y = 0; y<height; y++){
            for (int x = 0; x<width; x++){
                ICWarsCellType color = ICWarsCellType.toType(getRGB(height-1-y,x));
                setCell(x,y, new ICWarsCell(x,y,color));
            }
        }
    }


    /**
     * Classe ICWarsCell
     * Définit les cellules des aires de jeu que l'on utilise dans le jeu ICWars
     */
    public class ICWarsCell extends AreaBehavior.Cell{

        /// Type of the cell following the enum
        private final ICWarsCellType type;


        /**
         Constructeur par défaut de la classe ICWarsCell
         * @param x (int) : coordonnée x de la cellule.
         * @param y (int) coordonnée y de la cellule.
         * @param type (ICWarsCellType), qui n'est pas NONE, type que l'on veut associer à une cellule.
         */
        public ICWarsCell(int x, int y, ICWarsCellType type){
            super(x,y);
            this.type = type;
        }


        /**
         * Méthode canEnter : définit si l'acteur principal, le curseur, peut entrer ou non dans une cellule.
         * @param entity (Interactable), not null : Ce paramètre correspond à l'acteur qui veut entrer
         * @return true ou false, selon si l'on peut ou non entrer dans la cellule.
         */
        @Override
        protected boolean canEnter(Interactable entity){
            /*
            /TODO
            On doit définir ici les conditions pour lesquelles une cellule refuserait son accès.
            Cet accès va probablement être restreint selon le nombre d'étoile de la cellule (ou de l'acteur qui l'occupe?)
            Cela a à voir également avec le fait qu'un acteur peut prendre l'espace
            => Revoir la fonction takeCellSpace





            Update(4.12.2021) https://piazza.com/class/ktijhp746sr283?cid=570

             */
            if (false) {
                return false;
            }
            else{
                //J'ai défini pour le moment que la méthode renvoie true de base.
                return true;
            }
        }

        /**
         *Cette méthode détermine si l'on peut ou pas quitter une cellule
         * @param entity (Interactable), not null Ce paramètre correspond à l'acteur (curseur en principe)
         * @return true ou false selon si on peut ou non quitter la cellule.
         */
        @Override
        protected boolean canLeave(Interactable entity){
            //Pour le moment, j'ai mis que l'on peut toujours quitter la cellule
            //Ce sera très probablement à modifier par la suite.
            return true;
        }

        /**
         * Méthode isCellInteractable
         * @return : retourne true si on peut interagir avec la cellule.
         */
        @Override
        public boolean isCellInteractable(){
            //retourne true tout le temps pour le moment, à modifier
            return true;
        }

        /**
         * Méthode isViewInteractable
         * @return : retourne true si l'on peut interagir à distance avec la cellule.
         */
        @Override
        public boolean isViewInteractable() {
            //Pour le moment, la méthode retourne false tout le temps, à modifier.
            return false;
        }

        /**
         * Méthode acceptInteraction
         * Cette méthode définit ce qu'il se passe si l'on accepte l'interaction (à vérifier)
         * @param v (AreaInteractionVisitor) : the visitor Le paramètre est le visiteur qui veut interagir (donc l'interacteur)
         */
        @Override
        public void acceptInteraction(AreaInteractionVisitor v) {
            //Méthode à définir
        }





    }



}
