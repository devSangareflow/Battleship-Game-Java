package battle.naval;
import java.util.Random;

public class JeuUtils {
    public final static int NBR_LIGNES_OU_COL_GRILLE = 8;
    public final static char SYMBOLE_VAISSEAU = 'B';

    //Differents jeux valides
    private final static String [] JEUX = {
            grille("         .....               .   ... .       .    .  .    .     "),
            grille(".     ...       .       .      .       .       .       ....    ."),
            grille("         . ...   .     . . .   . . .   . .     .                "),
            grille("              . ..... .       .       .          ..         ... "),
            grille(" .       .   .       .    .  .    .  .  . .  .  . .     .       "),
            grille("                 . ....  .       .        .....        .       ."),
            grille("   . ...   .              .....                  ....           "),
            grille("    ....        .       . .       .       .       . ...   .     "),
            grille("                      .. .....          .   .....       .       "),
            grille(". ..... .          ...              .       .       .       .   ")
    };

    /**
     * Retourne  une chaine de caracteres representant une grille
     * dans laquelle sont positionnes les 4 vaisseaux : cuirasse, croiseur,
     * sous-marin, destroyer.
     *
     * Dans la chaine retournee, les indices contenant une partie d'un vaisseau
     * contiennent le caractere SYMBOLE_VAISSEAU, tandis que les autres indices
     * contiennent le caractere ' ' (espace).
     *
     * La chaine retournee contient toujours 64 caracteres pour representer
     * une grille NBR_LIGNES_OU_COL_GRILLE X NBR_LIGNES_OU_COL_GRILLE.
     *
     * @return la chaine construite representant une grille solution
     */
    public static String genererGrilleSolution () {
        int indice = tirerUnNombreEntreMinEtMax(0, JEUX.length - 1);
        return JEUX[indice];
    }

    /**
     * Affiche la chaine donnee sous forme d'une grille de dimensions
     * NBR_LIGNES_OU_COL_GRILLE X NBR_LIGNES_OU_COL_GRILLE dans laquelle
     * chaque caractere de la chaine donne est dans une case de la grille.
     *
     * Si la chaine donnee ne contient que des espaces, la grille affichee sera une
     * grille vide de dimensions NBR_LIGNES_OU_COL_GRILLE X NBR_LIGNES_OU_COL_GRILLE.
     *
     * ANT : La grille donnee doit contenir exactement 64 caracteres pour que
     *       celle-ci soit affichee correctement.
     *
     * @param grille la chaine representant la grille a afficher.
     */
    public static void afficherGrille (String grille) {
        String ligne = ligne(33, '-');
        int compteur = 0;

        System.out.println("    0   1   2   3   4   5   6   7");
        System.out.println ("  " + ligne);
        for (int i = 0 ; i < NBR_LIGNES_OU_COL_GRILLE ; i++) {
            System.out.print(i + " |");
            for (int j = 0 ; j < NBR_LIGNES_OU_COL_GRILLE ; j++) {
                System.out.print(" " + grille.charAt(compteur) + " |");
                compteur++;
            }
            System.out.println ("\n  " + ligne);
        }

    }


    /********************************
     * METHODES PRIVEES
     ********************************/

    /**
     * Construit une grille solution avec le modele passee en parametre.
     * @param modele le modele servant a construire la grille.
     * @return la grille solution construite a partir du modele donne.
     */
    private static String grille(String modele) {
        String grille = "";
        grille = modele.replace('.', SYMBOLE_VAISSEAU);
        return grille;
    }

    /**
     * Genere un nombre aleatoire entre min et max.
     * @param min la borne minimale (incluse) pour la generation du nombre
     * @param max la borne maximale (incluse) pour la generation du nombre
     * @return un nombre aleatoire entre min et max.
     */
    private static int tirerUnNombreEntreMinEtMax(int min, int max) {
        Random generateurAlea = new Random();
        return generateurAlea.nextInt(max - min + 1) + min;
    }

    /**
     * Retourne une chaine formee de lng caracteres c.
     * @param lng la longueur de la chaine retournee.
     * @param c le caracteres a repeter lng fois dans la chaine retourner.
     * @return une chaine formee de lng caracteres c.
     */
    private static String ligne (int lng, char c) {
        String ligne = "";
        for (int i = 0 ; i < lng ; i++) {
            ligne = ligne + c;
        }
        return ligne;
    }
}
