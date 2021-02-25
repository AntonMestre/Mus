package com.montaury.mus.jeu;

import com.montaury.mus.jeu.joueur.AffichageEvenementsDeJeu;
import com.montaury.mus.jeu.joueur.Equipe;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Partie {
  private final AffichageEvenementsDeJeu affichage;

  public Partie(AffichageEvenementsDeJeu affichage) {
    this.affichage = affichage;
  }

  public Resultat jouer(Opposants opposants) {
    affichage.nouvellePartie();
    Partie.Score score = new Partie.Score(opposants);

    if (opposants.jouentEnEquipe()) {
      Optional<Equipe> equipeVainqueure;
      do {
        Manche.Resultat resultat = new Manche(affichage).jouer(opposants);
        equipeVainqueure = score.enregistrerEquipe(resultat);
        affichage.mancheTerminee(score);
      } while (equipeVainqueure.isEmpty());
      return new Resultat(equipeVainqueure.get(), score);
    }
    else {
      Optional<Joueur> vainqueur;
      do {
        Manche.Resultat resultat = new Manche(affichage).jouer(opposants);
        vainqueur = score.enregistrer(resultat);
        affichage.mancheTerminee(score);
      } while (vainqueur.isEmpty());
      return new Resultat(vainqueur.get(), score);
    }
  }

  public static class Score {
    private static final int NB_MANCHES_A_GAGNER = 3;

    private final List<Manche.Resultat> resultatManches = new ArrayList<>();
    private Map<Joueur, Integer> manchesGagneesParJoueur;
    private Map<Equipe, Integer> manchesGagneesParEquipe;

    private boolean estUnScoreDEquipe;

    public Score(Opposants opposants) {
      if (opposants.jouentEnEquipe()) {
        manchesGagneesParEquipe = new HashMap<>();
        this.manchesGagneesParEquipe.put(opposants.joueurEsku().getEquipe(), 0);
        this.manchesGagneesParEquipe.put(opposants.joueurZaku().getEquipe(), 0);

        estUnScoreDEquipe = true;
      }
      else {
        manchesGagneesParJoueur = new HashMap<>();
        this.manchesGagneesParJoueur.put(opposants.joueurEsku(), 0);
        this.manchesGagneesParJoueur.put(opposants.joueurZaku(), 0);

        estUnScoreDEquipe = false;
      }
    }

    public Optional<Joueur> enregistrer(Manche.Resultat score) {
      resultatManches.add(score);
      manchesGagneesParJoueur.put(score.vainqueur(), manchesGagneesParJoueur.get(score.vainqueur()) + 1);
      return vainqueur();
    }

    public Optional<Equipe> enregistrerEquipe(Manche.Resultat score) {
      resultatManches.add(score);
      manchesGagneesParEquipe.put(score.equipeVainqueure(), manchesGagneesParEquipe.get(score.equipeVainqueure()) + 1);
      return equipeVainqueure();
    }

    public List<Manche.Resultat> resultatManches() {
      return resultatManches;
    }

    public boolean estUnScoreDEquipe() { return estUnScoreDEquipe; }

    public Optional<Joueur> vainqueur() {
      return manchesGagneesParJoueur.keySet().stream().filter(joueur -> manchesGagneesParJoueur.get(joueur) == NB_MANCHES_A_GAGNER).findAny();
    }

    public Optional<Equipe> equipeVainqueure() {
      return manchesGagneesParEquipe.keySet().stream().filter(equipe -> manchesGagneesParEquipe.get(equipe) == NB_MANCHES_A_GAGNER).findAny();
    }
  }

  public static class Resultat {
    private Joueur vainqueur;
    private Equipe equipeVainqueure;
    private Score score;

    private boolean estUnResultatDEquipe;

    private Resultat(Joueur vainqueur, Score score) {
      this.vainqueur = vainqueur;
      this.score = score;

      this.estUnResultatDEquipe = false;
    }

    private Resultat(Equipe vainqueurs, Score score) {
      this.equipeVainqueure = vainqueurs;
      this.score = score;

      this.estUnResultatDEquipe = true;
    }

    public Joueur vainqueur() {
      return vainqueur;
    }
    public Equipe equipeVainqueure() { return equipeVainqueure; }
    public boolean estUnResultatDEquipe() { return estUnResultatDEquipe; }

    public Score score() {
      return score;
    }
  }
}
