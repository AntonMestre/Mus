package com.montaury.mus.jeu;

import com.montaury.mus.jeu.joueur.AffichageEvenementsDeJeu;
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
    Optional<Joueur> vainqueur;
    do {
      Manche.Resultat resultat = new Manche(affichage).jouer(opposants);
      vainqueur = score.enregistrer(resultat,opposants);
      affichage.mancheTerminee(score);
    } while (vainqueur.isEmpty());
    return new Resultat(vainqueur.get(), score);
  }

  public static class Score {
    private static final int NB_MANCHES_A_GAGNER = 3;

    private final List<Manche.Resultat> resultatManches = new ArrayList<>();
    private final Map<Joueur, Integer> manchesGagneesParJoueur = new HashMap<>();

    public Score(Opposants opposants) {
      this.manchesGagneesParJoueur.put(opposants.joueurEsku(), 0);
      if(opposants.isJeuEnEquipe()) {
        this.manchesGagneesParJoueur.put(opposants.joueurPriorite2(), 0);
        this.manchesGagneesParJoueur.put(opposants.joueurPriorite3(), 0);
      }
      this.manchesGagneesParJoueur.put(opposants.joueurZaku(), 0);
    }

    public Optional<Joueur> enregistrer(Manche.Resultat score,Opposants opposants) {
      resultatManches.add(score);
      manchesGagneesParJoueur.put(score.vainqueur(), manchesGagneesParJoueur.get(score.vainqueur()) + 1);
      return vainqueur(opposants);
    }

    public List<Manche.Resultat> resultatManches() {
      return resultatManches;
    }

    public Optional<Joueur> vainqueur(Opposants opposants) {

      if(opposants.isJeuEnEquipe()){
          int scoreEquipeEsku = manchesGagneesParJoueur.get(opposants.joueurEsku()) + manchesGagneesParJoueur.get(opposants.joueurPriorite2());
          int scoreEquipeZaku = manchesGagneesParJoueur.get(opposants.joueurZaku()) + manchesGagneesParJoueur.get(opposants.joueurPriorite3());

          if(scoreEquipeEsku == NB_MANCHES_A_GAGNER){
            return Optional.ofNullable(opposants.joueurEsku());
          }else if (scoreEquipeZaku == NB_MANCHES_A_GAGNER ) {
            return Optional.ofNullable(opposants.joueurZaku());
          }

      }else {
        return manchesGagneesParJoueur.keySet().stream().filter(joueur -> manchesGagneesParJoueur.get(joueur) == NB_MANCHES_A_GAGNER).findAny();
      }

      return null;
    }
  }

  public static class Resultat {
    private final Joueur vainqueur;
    private final Score score;

    private Resultat(Joueur vainqueur, Score score) {
      this.vainqueur = vainqueur;
      this.score = score;
    }

    public Joueur vainqueur() {
      return vainqueur;
    }

    public Score score() {
      return score;
    }
  }
}
