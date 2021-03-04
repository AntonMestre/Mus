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
    Optional<Equipe> equipeVainqueure;
    do {
      Manche.Resultat resultat = new Manche(affichage).jouer(opposants);
      equipeVainqueure = score.enregistrerEquipe(resultat);
      affichage.mancheTerminee(score);
    } while (equipeVainqueure.isEmpty());
    return new Resultat(equipeVainqueure.get(), score);
  }

  public static class Score {
    private static final int NB_MANCHES_A_GAGNER = 3;

    private final List<Manche.Resultat> resultatManches = new ArrayList<>();
    private Map<Equipe, Integer> manchesGagneesParEquipe;

    public Score(Opposants opposants) {
        manchesGagneesParEquipe = new HashMap<>();
        this.manchesGagneesParEquipe.put(opposants.joueurEsku().getEquipe(), 0);
        this.manchesGagneesParEquipe.put(opposants.joueurZaku().getEquipe(), 0);
    }

    public Optional<Equipe> enregistrerEquipe(Manche.Resultat score) {
      resultatManches.add(score);
      manchesGagneesParEquipe.put(score.equipeVainqueure(), manchesGagneesParEquipe.get(score.equipeVainqueure()) + 1);
      return equipeVainqueure();
    }

    public List<Manche.Resultat> resultatManches() {
      return resultatManches;
    }

    public Optional<Equipe> equipeVainqueure() {
      return manchesGagneesParEquipe.keySet().stream().filter(equipe -> manchesGagneesParEquipe.get(equipe) == NB_MANCHES_A_GAGNER).findAny();
    }
  }

  public static class Resultat {
    private Equipe equipeVainqueure;
    private Score score;

    private Resultat(Equipe vainqueurs, Score score) {
      this.equipeVainqueure = vainqueurs;
      this.score = score;
    }

    public Equipe equipeVainqueure() { return equipeVainqueure; }

    public Score score() {
      return score;
    }
  }
}
