package com.montaury.mus.jeu;

import com.montaury.mus.jeu.joueur.AffichageEvenementsDeJeu;
import com.montaury.mus.jeu.joueur.Equipe;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;
import com.montaury.mus.jeu.tour.Tour;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Manche {
  private final AffichageEvenementsDeJeu affichage;

  public Manche(AffichageEvenementsDeJeu affichage) {
    this.affichage = affichage;
  }

  public Resultat jouer(Opposants opposants) {
    affichage.nouvelleManche();
    Score score = new Score(opposants);
    do {
      new Tour(affichage).jouer(opposants, score);
      affichage.tourTermine(opposants, score);
      opposants.tourner();
    } while (score.equipeVainqueure().isEmpty());
    return new Resultat(score.equipeVainqueure().get(), score.pointsEquipeVaincue().get());
  }

  public static class Score {
    private static final int POINTS_POUR_TERMINER_MANCHE = 40;
    private Map<Equipe, Integer> scoreParEquipe;

    public Score(Opposants opposants) {
      scoreParEquipe = new HashMap<>();
      scoreParEquipe.put(opposants.joueurEsku().getEquipe(), 0);
      scoreParEquipe.put(opposants.joueurZaku().getEquipe(), 0);
    }

    public Map<Equipe, Integer> scoreParEquipe() { return scoreParEquipe; }

    public void scorer(Equipe equipe, int points) {
      if (equipeVainqueure().isEmpty()) {
        scoreParEquipe.put(equipe, Math.min(scoreParEquipe.get(equipe) + points, POINTS_POUR_TERMINER_MANCHE));
      }
    }

    public void remporterManche(Equipe equipe) {
      scoreParEquipe.put(equipe, POINTS_POUR_TERMINER_MANCHE);
    }

    public Optional<Equipe> equipeVainqueure() {
      return scoreParEquipe.keySet().stream().filter(equipe -> scoreParEquipe.get(equipe) == POINTS_POUR_TERMINER_MANCHE).findAny();
    }

    public Optional<Integer> pointsEquipeVaincue() {
      return equipeVainqueure().isEmpty() ?
              Optional.empty() :
              scoreParEquipe.values().stream().filter(points -> points < POINTS_POUR_TERMINER_MANCHE).findAny();
    }
  }

  public static class Resultat {
    private Equipe equipeVainqueure;
    private int pointsVaincu;

    public Resultat(Equipe equipe, int pointsVaincu) {
      equipeVainqueure = equipe;
      this.pointsVaincu = pointsVaincu;
    }

    public Equipe equipeVainqueure() {return equipeVainqueure; }

    public int pointsVaincu() {
      return pointsVaincu;
    }
  }
}
