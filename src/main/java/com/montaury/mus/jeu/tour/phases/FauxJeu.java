package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.joueur.Equipe;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;

import static com.montaury.mus.jeu.tour.phases.Jeu.aLeJeu;

public class FauxJeu extends Phase {
  public FauxJeu() {
    super("Faux Jeu");
  }

  @Override
  protected boolean peutParticiper(Joueur joueur) {
    return !aLeJeu(joueur);
  }

  @Override
  public boolean peutSeDerouler(Opposants opposants) {
    return (peutParticiper(opposants.joueurEsku()) && peutParticiper(opposants.joueurPriorite3())) && peutParticiper(opposants.joueurPriorite2()) && peutParticiper(opposants.joueurZaku());
  }

  @Override
  protected Joueur meilleurParmi(Opposants opposants) {
    Joueur meilleurJoueurEquipe1 = opposants.joueurEsku().main().pointsPourJeu() >= opposants.joueurPriorite3().main().pointsPourJeu() ? opposants.joueurEsku() : opposants.joueurPriorite3();
    Joueur meilleurJoueurEquipe2 = opposants.joueurPriorite2().main().pointsPourJeu() >= opposants.joueurZaku().main().pointsPourJeu() ? opposants.joueurPriorite2() : opposants.joueurZaku();

    return meilleurJoueurEquipe1.main().pointsPourJeu() >= meilleurJoueurEquipe2.main().pointsPourJeu() ? meilleurJoueurEquipe1 : meilleurJoueurEquipe2;
  }

  @Override
  public int pointsBonus(Joueur vainqueur) {
    return 1;
  }

  @Override
  public int pointsBonus(Equipe vainqueur) {
    return 1;
  }
}
