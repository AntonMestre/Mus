package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.carte.ValeurCarte;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Main;
import com.montaury.mus.jeu.joueur.Opposants;
import java.util.List;

import static com.montaury.mus.jeu.carte.ValeurCarte.Comparaison.PLUS_GRANDE;
import static com.montaury.mus.jeu.carte.ValeurCarte.Comparaison.PLUS_PETITE;

public class Grand extends Phase {
  public Grand() {
    super("Grand");
  }

  @Override
  protected Joueur meilleurParmi(Opposants opposants) {
      Joueur meilleurJoueurEquipe1 = comparerDeuxJoueurs(opposants.joueurEsku(), opposants.joueurPriorite3());
      Joueur meilleurJoueurEquipe2 = comparerDeuxJoueurs(opposants.joueurPriorite2(), opposants.joueurZaku());

      return comparerDeuxJoueurs(meilleurJoueurEquipe1, meilleurJoueurEquipe2);
  }

  protected Joueur comparerDeuxJoueurs(Joueur joueur1, Joueur joueur2) {
    List<Carte> cartesJoueur1 = joueur1.main().cartesDuPlusGrandAuPlusPetit();
    List<Carte> cartesJoueur2 = joueur2.main().cartesDuPlusGrandAuPlusPetit();

    for (int i = 0; i < Main.TAILLE; i++) {
      ValeurCarte.Comparaison compare = cartesJoueur1.get(i).comparerAvec(cartesJoueur2.get(i));
      if (compare == PLUS_GRANDE) {
        return joueur1;
      }
      if (compare == PLUS_PETITE) {
        return joueur2;
      }
    }
    // Si il y a égalité, on considère que le 1er joueur passé en paramètre est prioritaire
    return joueur1;
  }
}
