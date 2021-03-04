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

      Joueur meilleurJoueurEquipe1 = pairesJoueurEsku.estMeilleureOuEgaleA(pairesJoueurPriorite3) ? opposants.joueurEsku() : opposants.joueurPriorite3();
      Joueur meilleurJoueurEquipe2 = pairesJoueurPriorite2.estMeilleureOuEgaleA(pairesJoueurZaku) ? opposants.joueurPriorite2() : opposants.joueurZaku();

      return meilleurJoueurEquipe1.main().getPaires().estMeilleureOuEgaleA(meilleurJoueurEquipe2.main().getPaires()) ? meilleurJoueurEquipe1 : meilleurJoueurEquipe2;
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
