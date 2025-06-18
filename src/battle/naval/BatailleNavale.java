package battle.naval;

/**
 * Projet : Jeu de Bataille Navale
 *
 * Ce programme simule une version simplifiée et solitaire du jeu de bataille navale.
 *
 * Fonctionnalités :
 * - Affiche une présentation ASCII du jeu.
 * - Permet au joueur de sélectionner un niveau de difficulté (débutant, intermédiaire, expert).
 * - Gère une grille 8x8 représentée par une chaîne de 64 caractères.
 * - Génère aléatoirement la grille de solution via la classe JeuUtils.
 * - Permet au joueur d'effectuer des tirs, avec détection de tirs réussis, manqués et redondants.
 * - Affiche B dans la case si le tir est réussi ou X sinon c'est à dire tir manqué.
 * - Compte le nombre de vaisseaux coulés et détermine la victoire ou la défaite.
 * - Offre la possibilité de rejouer après chaque partie avec une validation stricte des entrées.
 *
 * Contraintes :
 * - Aucune utilisation de tableaux ou de collections.
 * - Toute la logique repose sur la manipulation des chaînes de caractères (classe String).
 *
 * @author ABDOULAYE SANGARE
 * @version 17-JUIN-2025
 */

public class BatailleNavale {

    public static final int PROJECTILE_DEBUTANT = 45;
    public static final int PROJECTILE_INTERMEDIAIRE = 35;
    public static final int PROJECTILE_EXPERT = 25;
    public static final int NB_TOTAL_BATEAUX = 14;
    public final static String PRESENTATION =
            " ____    ____  ______   ____  ____  _      _        ___ \n" +
                    "|    \\  /    ||      | /    ||    || |    | |      /  _]\n" +
                    "|  o  )|  o  ||      ||  o  | |  | | |    | |     /  [_ \n" +
                    "|     ||     ||_|  |_||     | |  | | |___ | |___ |    _]\n" +
                    "|  O  ||  _  |  |  |  |  _  | |  | |     ||     ||   [_ \n" +
                    "|     ||  |  |  |  |  |  |  | |  | |     ||     ||     |\n" +
                    "|_____||__|__|  |__|  |__|__||____||_____||_____||_____|\n" +
                    "       ____    ____  __ __   ____  _        ___           \n" +
                    "      |    \\  /    ||  |  | /    || |      /  _]          \n" +
                    "      |  _  ||  o  ||  |  ||  o  || |     /  [_           \n" +
                    "      |  |  ||     ||  |  ||     || |___ |    _]          \n" +
                    "      |  |  ||  _  ||  :  ||  _  ||     ||   [_           \n" +
                    "      |  |  ||  |  | \\   / |  |  ||     ||     |          \n" +
                    "      |__|__||__|__|  \\_/  |__|__||_____||_____|    ";

    /**
     * Cette méthode affiche le nom du jeu en art ASCII et
     * demande au joueur de taper sur Entrée pour continuer.
     */
    public static void afficherPresentation () {
        System.out.println(PRESENTATION);
        System.out.println();
        System.out.println("Appuyez sur <ENTREE> pour continuer...");
        Clavier.lireString();
    }

    /**
     *
     */
    public static int demanderNiveau () {
        int munitions = 0;
        boolean valide = false;

        while(!valide) {
            System.out.println();
            System.out.println("NIVEAU DE DIFFICULTE :");
            System.out.println();
            System.out.println("1. DEBUTANT (45 munitions)");
            System.out.println("2. INTERMEDIAIRE (35 munitions)");
            System.out.println("3. EXPERT (25 munitions)");
            System.out.println();
            System.out.println("Entrez votre choix (1, 2 ou 3)");

            char choix = Clavier.lireCharLn();
            switch (choix) {
                case '1' :
                    munitions = PROJECTILE_DEBUTANT;
                    valide = true;
                    break;
                case '2' :
                    munitions = PROJECTILE_INTERMEDIAIRE;
                    valide = true;
                    break;
                case '3' :
                    munitions = PROJECTILE_EXPERT;
                    valide = true;
                    break;
                default :
                    System.out.println("ERREUR, choix invalide...veuillez réesayez");
            }
        }
        return munitions;
    }

    /**
     * Initialise la grille du joueur (grille vide de 64 cases).
     * @return Une chaîne de 64 espaces représentant la grille vide.
     */
    public static String initialiserGrilleJoueur() {
        String grille = "";
        for (int i = 0; i < 64; i++) {
            grille += " ";
        }
        return grille;
    }

    /**
     * Demande et valide les coordonnées du tir.
     * Retourne l'index correspondant dans la chaîne (entre 0 et 63).
     */
    public static int demanderCoordonnees() {
        int index = -1;
        boolean valide = false;

        while (!valide) {
            System.out.print("Entrez les coordonnees du prochain tir (format x,y) : ");
            String saisie = Clavier.lireString();

            if (saisie.length() == 3 &&
                    saisie.charAt(1) == ',' &&
                    Character.isDigit(saisie.charAt(0)) &&
                    Character.isDigit(saisie.charAt(2))) {

                int ligne = Character.getNumericValue(saisie.charAt(0));
                int colonne = Character.getNumericValue(saisie.charAt(2));

                if (ligne >= 0 && ligne <= 7 && colonne >= 0 && colonne <= 7) {
                    index = ligne * 8 + colonne;
                    valide = true;
                } else {
                    System.out.println("ERREUR : coordonnées hors limites.");
                }

            } else {
                System.out.println("ERREUR : format invalide. Exemple valide : 3,4");
            }
        }

        return index;
    }

    /**
     * Remplace un caractère à un index donné dans une String.
     */
    public static String remplacerCaractere(String chaine, int index, char nouveauCaractere) {
        return chaine.substring(0, index) + nouveauCaractere + chaine.substring(index + 1);
    }

    /**
     * Traite un tir du joueur et met à jour la grille joueur.
     * @param index l'index du tir dans la grille
     * @param grilleSolution la grille solution (où sont les bateaux)
     * @param grilleJoueur la grille du joueur (les tirs déjà faits)
     * @return la nouvelle grille du joueur mise à jour
     */
    public static String traiterTir(int index, String grilleSolution, String grilleJoueur) {
        char caseJoueur = grilleJoueur.charAt(index);
        char caseSolution = grilleSolution.charAt(index);

        if (caseJoueur == 'B' || caseJoueur == 'X') {
            System.out.println("TIR REDONDANT : vous avez déjà tiré ici.");
            return grilleJoueur; // pas de changement
        }

        if (caseSolution == 'B') {
            System.out.println("----------> TIR REUSSI ! <----------");
            grilleJoueur = remplacerCaractere(grilleJoueur, index, 'B');
        } else {
            System.out.println("TIR MANQUE !");
            grilleJoueur = remplacerCaractere(grilleJoueur, index, 'X');
        }

        return grilleJoueur;
    }

    /**
     * Demande au joueur s'il veut rejouer.
     * Retourne true si oui, false si non.
     */
    public static boolean demanderRejouer() {
        while (true) {
            System.out.print("Voulez-vous rejouer (oui/non)? ");
            String saisie = Clavier.lireString().toLowerCase();

            if (saisie.equals("oui")) {
                return true;
            } else if (saisie.equals("non")) {
                return false;
            } else {
                System.out.println("ERREUR : veuillez entrer exactement 'oui' ou 'non'.");
            }
        }
    }

    public static void main (String [] params) {

        do {
            afficherPresentation();

            int munitions = demanderNiveau();
            System.out.println("Vous avez sélectionné le niveau avec" + " "+ munitions + " " + " munitions.");

            String grilleJoueur = initialiserGrilleJoueur();
            String grilleSolution = JeuUtils.genererGrilleSolution();

            int nbTirsReussis = 0;

            while (munitions > 0 && nbTirsReussis < NB_TOTAL_BATEAUX) {
                JeuUtils.afficherGrille(grilleJoueur);
                int index = demanderCoordonnees();

                if (grilleSolution.charAt(index) == 'B'
                        && grilleJoueur.charAt(index) != 'B') {
                    nbTirsReussis++;
                }

                grilleJoueur = traiterTir(index, grilleSolution, grilleJoueur);
                munitions--;
                System.out.println("Munitions restantes : " + munitions);
            }

                if (nbTirsReussis == NB_TOTAL_BATEAUX) {
                      System.out.println("BRAVO! Vous avez détruit la flotte ennemie!");
               } else {
                      int casesRestantes = NB_TOTAL_BATEAUX - nbTirsReussis;
                      System.out.println("DÉSOLÉ! Vous avez épuisé toutes vos munitions!");
                      System.out.println("Nombre de cases de vaisseaux non touchées : " + casesRestantes);
             }


        } while (demanderRejouer());

        System.out.println("Merci d'avoir joué ! Au revoir !");
    }

}
