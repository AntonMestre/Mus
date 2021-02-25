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

    if (opposants.jouentEnEquipe()) {
      do {
        new Tour(affichage).jouer(opposants, score);
        affichage.tourTermine(opposants, score);
        opposants.tourner();
      } while (score.equipeVainqueure().isEmpty());
      return new Resultat(score.equipeVainqueure().get(), score.pointsEquipeVaincue().get());
    }
    else {
      do {
        new Tour(affichage).jouer(opposants, score);
        affichage.tourTermine(opposants, score);
        opposants.tourner();
      } while (score.vainqueur().isEmpty());
      return new Resultat(score.vainqueur().get(), score.pointsVaincu().get());
    }
  }

  public static class Score {
    private static final int POINTS_POUR_TERMINER_MANCHE = 40;

    private Map<Joueur, Integer> scoreParJoueur;
    private Map<Equipe, Integer> scoreParEquipe;

    private boolean estUnScoreDEquipe;

    public Score(Opposants opposants) {
      if (opposants.jouentEnEquipe()) {
        this.estUnScoreDEquipe = true;
        scoreParEquipe = new HashMap<>();
        scoreParEquipe.put(opposants.joueurEsku().getEquipe(), 0);
        scoreParEquipe.put(opposants.joueurZaku().getEquipe(), 0);
      }
      else {
        this.estUnScoreDEquipe = false;
        scoreParJoueur = new HashMap<>();
        scoreParJoueur.put(opposants.joueurEsku(), 0);
        scoreParJoueur.put(opposants.joueurZaku(), 0);
      }
    }

    public Map<Joueur, Integer> scoreParJoueur() {
      return scoreParJoueur;
    }
    public Map<Equipe, Integer> scoreParEquipe() { return scoreParEquipe; }
    public boolean estUnScoreDEquipe() { return estUnScoreDEquipe; }

    public void scorer(Joueur joueur, int points) {
      if (vainqueur().isEmpty()) {
        scoreParJoueur.put(joueur, Math.min(scoreParJoueur.get(joueur) + points, POINTS_POUR_TERMINER_MANCHE));
      }
    }

    public void scorer(Equipe equipe, int points) {
      if (equipeVainqueure().isEmpty()) {
        scoreParEquipe.put(equipe, Math.min(scoreParEquipe.get(equipe) + points, POINTS_POUR_TERMINER_MANCHE));
      }
    }

    public void remporterManche(Joueur joueur) {
      scoreParJoueur.put(joueur, POINTS_POUR_TERMINER_MANCHE);
    }
    public void remporterManche(Equipe equipe) {
      scoreParEquipe.put(equipe, POINTS_POUR_TERMINER_MANCHE);
    }

    public Optional<Joueur> vainqueur() {
      return scoreParJoueur.keySet().stream().filter(joueur -> scoreParJoueur.get(joueur) == POINTS_POUR_TERMINER_MANCHE).findAny();
    }
    public Optional<Equipe> equipeVainqueure() {
      return scoreParEquipe.keySet().stream().filter(equipe -> scoreParEquipe.get(equipe) == POINTS_POUR_TERMINER_MANCHE).findAny();
    }

    public Optional<Integer> pointsVaincu() {
      return vainqueur().isEmpty() ?
        Optional.empty() :
        scoreParJoueur.values().stream().filter(points -> points < POINTS_POUR_TERMINER_MANCHE).findAny();
    }
    public Optional<Integer> pointsEquipeVaincue() {
      return equipeVainqueure().isEmpty() ?
              Optional.empty() :
              scoreParEquipe.values().stream().filter(points -> points < POINTS_POUR_TERMINER_MANCHE).findAny();
    }
  }

  public static class Resultat {
    private Joueur vainqueur;
    private Equipe equipeVainqueure;
    private int pointsVaincu;

    public Resultat(Joueur joueur, int pointsVaincu) {
      vainqueur = joueur;
      this.pointsVaincu = pointsVaincu;
    }

    public Resultat(Equipe equipe, int pointsVaincu) {
      equipeVainqueure = equipe;
      this.pointsVaincu = pointsVaincu;
    }

    public Joueur vainqueur() {
      return vainqueur;
    }

    public Equipe equipeVainqueure() {return equipeVainqueure; }

    public int pointsVaincu() {
      return pointsVaincu;
    }
  }
}
