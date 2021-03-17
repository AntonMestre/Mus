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
    Joueur meilleurJoueurEquipe1 = opposants.joueurEsku();
    Joueur meilleurJoueurEquipe2 = opposants.joueurPriorite2();

    if (opposants.joueurEsku().main().aDesPaires() && opposants.joueurPriorite3().main().aDesPaires()) {
      meilleurJoueurEquipe1 = opposants.joueurEsku().main().getPaires().estMeilleureOuEgaleA(opposants.joueurPriorite3().main().getPaires()) ? opposants.joueurEsku() : opposants.joueurPriorite3();
    }
    else if (opposants.joueurEsku().main().aDesPaires() && !opposants.joueurPriorite3().main().aDesPaires()) {
      meilleurJoueurEquipe1 = opposants.joueurEsku();
    }
    else if (!opposants.joueurEsku().main().aDesPaires() && opposants.joueurPriorite3().main().aDesPaires()) {
      meilleurJoueurEquipe1 = opposants.joueurPriorite3();
    }
    else if (!opposants.joueurEsku().main().aDesPaires() && !opposants.joueurPriorite3().main().aDesPaires()) {
      return opposants.joueurPriorite2().main().getPaires().estMeilleureOuEgaleA(opposants.joueurZaku().main().getPaires()) ? opposants.joueurPriorite2() : opposants.joueurZaku();
    }

    if (opposants.joueurPriorite2().main().aDesPaires() && opposants.joueurZaku().main().aDesPaires()) {
      meilleurJoueurEquipe2 = opposants.joueurPriorite2().main().getPaires().estMeilleureOuEgaleA(opposants.joueurEsku().main().getPaires()) ? opposants.joueurPriorite2() : opposants.joueurEsku();
    }
    else if (opposants.joueurPriorite2().main().aDesPaires() && !opposants.joueurZaku().main().aDesPaires()) {
      meilleurJoueurEquipe2 = opposants.joueurPriorite2();
    }
    else if (!opposants.joueurPriorite2().main().aDesPaires() && opposants.joueurZaku().main().aDesPaires()) {
      meilleurJoueurEquipe2 = opposants.joueurZaku();
    }
    else if (!opposants.joueurPriorite2().main().aDesPaires() && !opposants.joueurZaku().main().aDesPaires()) {
      return opposants.joueurEsku().main().getPaires().estMeilleureOuEgaleA(opposants.joueurEsku().main().getPaires()) ? opposants.joueurPriorite2() : opposants.joueurEsku();
    }
    return meilleurJoueurEquipe1.main().getPaires().estMeilleureOuEgaleA(meilleurJoueurEquipe2.main().getPaires()) ? meilleurJoueurEquipe1 : meilleurJoueurEquipe2;
  }

  @Override
  public int pointsBonus(Joueur vainqueur) {
    return vainqueur.main().getPaires().pointsBonus();
  }
}
