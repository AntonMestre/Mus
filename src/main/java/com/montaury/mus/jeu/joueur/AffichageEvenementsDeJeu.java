package com.montaury.mus.jeu.joueur;

import com.montaury.mus.jeu.Manche;
import com.montaury.mus.jeu.Partie;
import com.montaury.mus.jeu.tour.phases.Phase;
import com.montaury.mus.jeu.tour.phases.dialogue.Choix;

public interface AffichageEvenementsDeJeu {
  void nouvellePartie();
  void nouvelleManche();
  void mancheTerminee(Partie.Score score);
  void nouveauTour(Opposants opposants);
  void tourTermine(Opposants opposants, Manche.Score score);
  void choixFait(Joueur joueur, Choix choix);

  void nouvelleMain(Joueur joueur);

  void nouvellePhase(Phase phase);
  void finPhase(Phase phase, Phase.Resultat resultat);
  void partieTerminee(Partie.Resultat resultat);
}
