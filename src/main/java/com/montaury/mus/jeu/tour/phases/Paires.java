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
    com.montaury.mus.jeu.carte.paires.Paires pairesJoueurEsku = opposants.joueurEsku().main().getPaires();
    com.montaury.mus.jeu.carte.paires.Paires pairesJoueurZaku = opposants.joueurZaku().main().getPaires();

    if (opposants.jouentEnEquipe()) {
      com.montaury.mus.jeu.carte.paires.Paires pairesJoueurPriorite2 = opposants.joueurPriorite2().main().getPaires();
      com.montaury.mus.jeu.carte.paires.Paires pairesJoueurPriorite3 = opposants.joueurPriorite3().main().getPaires();

      if (pairesJoueurEsku.estMeilleureOuEgaleA(pairesJoueurZaku)
          && pairesJoueurEsku.estMeilleureOuEgaleA(pairesJoueurPriorite2)
          && pairesJoueurEsku.estMeilleureOuEgaleA(pairesJoueurPriorite3)) {
        return opposants.joueurEsku();
      }
      else if (pairesJoueurPriorite2.estMeilleureOuEgaleA(pairesJoueurEsku)
              && pairesJoueurPriorite2.estMeilleureOuEgaleA(pairesJoueurPriorite3)
              && pairesJoueurPriorite2.estMeilleureOuEgaleA(pairesJoueurZaku)) {
        return opposants.joueurPriorite2();
      }
      else if (pairesJoueurPriorite3.estMeilleureOuEgaleA(pairesJoueurEsku)
              && pairesJoueurPriorite3.estMeilleureOuEgaleA(pairesJoueurPriorite2)
              && pairesJoueurPriorite3.estMeilleureOuEgaleA(pairesJoueurZaku)) {
        return opposants.joueurPriorite3();
      }
      else {
        return opposants.joueurZaku();
      }
    }
    else {
      return pairesJoueurEsku.estMeilleureOuEgaleA(pairesJoueurZaku) ? opposants.joueurEsku() : opposants.joueurZaku();
    }
  }

  @Override
  public int pointsBonus(Joueur vainqueur) {
    return vainqueur.main().getPaires().pointsBonus();
  }
}
