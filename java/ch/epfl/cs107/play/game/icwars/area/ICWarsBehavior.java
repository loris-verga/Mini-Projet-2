package ch.epfl.cs107.play.game.icwars.area;

import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.tutosSolution.Tuto2Behavior;


/**
 * Classe ICWarsBehavior:
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

        @Override
        protected boolean canEnter(Interactable entity){
            /*
            /TODO
            On doit définir ici les conditions pour lesquelles une cellule refuserait son accès.
            Cet accès va probablement être restreint selon le nombre d'étoile de la cellule (ou de l'acteur qui l'occupe?)
            Cela a à voir également avec le fait qu'un acteur peut prendre l'espace
            => Revoir la fonction takeCellSpace
             */
            if (false) {
                return false;
            }
            else{
                //J'ai défini pour le moment que la méthode renvoie true de base.
                return true;
            }
        }






    }



}
