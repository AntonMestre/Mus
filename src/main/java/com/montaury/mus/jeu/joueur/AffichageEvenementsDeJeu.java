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
  void finPhase(String nomEquipeVainqueure, int pointsBonus);
  void aucunJoueurNePeutParticiper();
  void unSeulJoueurPeutParticiper(String nomJoueur, int pointsBonusDuJoueur);
  void deuxJoueursDeLaMemeEquipePeuventParticiper(String j1, String j2, int ptsBonus1, int ptsBonus2);
  void partieTerminee(Partie.Resultat resultat);
}
