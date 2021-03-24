package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;

public class Paires extends Phase {
  public Paires() {
    super("Paires");
  }

  @Override
  protected boolean peutParticiper(Joueur joueur) {
    return joueur.main().aDesPaires();
  }

  @Override
  protected Joueur meilleurParmi(Opposants opposants) {
    Joueur meilleurJoueurEquipe1 = comparerDeuxJoueurs(opposants.joueurEsku(), opposants.joueurPriorite3());
    Joueur meilleurJoueurEquipe2 = comparerDeuxJoueurs(opposants.joueurPriorite2(), opposants.joueurZaku());

    return comparerDeuxJoueurs(meilleurJoueurEquipe1, meilleurJoueurEquipe2);
  }

  private Joueur comparerDeuxJoueurs(Joueur joueur1, Joueur joueur2) {
    if (joueur1.main().aDesPaires() && joueur2.main().aDesPaires()) {
      return joueur1.main().getPaires().estMeilleureOuEgaleA(joueur2.main().getPaires()) ? joueur1 : joueur2;
    }
    else if (joueur1.main().aDesPaires() && !joueur2.main().aDesPaires()) return joueur1;
    else if (!joueur1.main().aDesPaires() && joueur2.main().aDesPaires()) return joueur2;
    return null;
  }

  @Override
  public int pointsBonus(Joueur vainqueur) {
    return vainqueur.main().getPaires().pointsBonus();
  }
}
