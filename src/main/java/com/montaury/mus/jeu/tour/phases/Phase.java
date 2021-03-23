package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.Manche;
import com.montaury.mus.jeu.joueur.AffichageEvenementsDeJeu;
import com.montaury.mus.jeu.joueur.Equipe;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;
import com.montaury.mus.jeu.tour.phases.dialogue.Dialogue;
import com.montaury.mus.jeu.tour.phases.dialogue.DialogueTermine;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.montaury.mus.jeu.tour.phases.dialogue.TypeChoix.KANTA;
import static com.montaury.mus.jeu.tour.phases.dialogue.TypeChoix.PASO;
import static com.montaury.mus.jeu.tour.phases.dialogue.TypeChoix.TIRA;

public abstract class Phase {
  private final String nom;

  protected Phase(String nom) {
    this.nom = nom;
  }

  public String nom() {
    return nom;
  }

  public final Resultat jouer(AffichageEvenementsDeJeu affichage, Opposants opposants, Manche.Score score) {
    affichage.nouvellePhase(this);
    List<Joueur> joueurs = participantsParmi(opposants);
    if (joueurs.isEmpty()) {
      return Resultat.nonJouable();
    }
    if (joueurs.size() == 1) {
      return Resultat.termine(joueurs.get(0).getEquipe(), pointsBonus(joueurs.get(0)));
    }
    if (joueurs.size() == 2 && joueurs.get(0).estDeLaMemeEquipeQue(joueurs.get(1))) {
      return Resultat.termine(joueurs.get(0).getEquipe(), pointsBonus(joueurs.get(0)) + pointsBonus(joueurs.get(1)));
    }
    DialogueTermine dialogue = new Dialogue().derouler(affichage, opposants);
    Resultat resultat = conclure(dialogue, score, opposants);
    affichage.finPhase(this,resultat);
    return resultat;
  }

  private Resultat conclure(DialogueTermine dialogue, Manche.Score score, Opposants opposants) {
    if (dialogue.estConcluPar(TIRA)) {
      Equipe equipeEmportantLaMise = dialogue.avantDernierJoueur().getEquipe();
      score.scorer(equipeEmportantLaMise, dialogue.pointsEngages());
      return Resultat.termine(equipeEmportantLaMise, pointsBonus(equipeEmportantLaMise));
    }
    if (dialogue.estConcluPar(KANTA)) {
      Equipe equipeVainqueure = meilleurParmi(opposants).getEquipe();
      score.remporterManche(equipeVainqueure);
      return Resultat.termine(equipeVainqueure, 0);
    }
    Equipe equipeVainqueurePhase = meilleurParmi(opposants).getEquipe();
    int bonus = pointsBonus(equipeVainqueurePhase);
    return Resultat.suspendu(equipeVainqueurePhase, dialogue.estConcluPar(PASO) && bonus != 0 ? 0 : dialogue.pointsEngages(), bonus);
  }

  public List<Joueur> participantsParmi(Opposants opposants) {
    return opposants.dansLOrdre().stream()
      .filter(this::peutParticiper)
      .collect(Collectors.toList());
  }

  public boolean peutSeDerouler(Opposants opposants) {
    return (peutParticiper(opposants.joueurEsku()) || peutParticiper(opposants.joueurPriorite3())) && (peutParticiper(opposants.joueurPriorite2()) || peutParticiper(opposants.joueurZaku()));
  }

  protected boolean peutParticiper(Joueur joueur) {
    return true;
  }

  protected abstract Joueur meilleurParmi(Opposants opposants);

  protected int pointsBonus(Joueur vainqueur) { return 0; }
  protected int pointsBonus(Equipe equipeVainqueure) { return 0; }

  public static class Resultat {
    public static Resultat nonJouable() {
      return new Resultat(null, 0, 0);
    }

    public static Resultat termine(Equipe equipeVainqueure, int bonusDeFin) {
      return new Resultat(equipeVainqueure, 0, bonusDeFin);
    }

    public static Resultat suspendu(Equipe equipeVainqueure, int pointsEnSuspens, int bonusDeFin) {
      return new Resultat(equipeVainqueure, pointsEnSuspens, bonusDeFin);
    }
    private Equipe equipeVainqueure;
    public final int pointsEnSuspens;
    public final int bonus;

    private Resultat(Equipe equipeVainqueure, int pointsEnSuspens, int bonus) {
      this.pointsEnSuspens = pointsEnSuspens;
      this.equipeVainqueure = equipeVainqueure;
      this.bonus = bonus;
    }

    public Optional<Equipe> equipeVainqueure() { return Optional.ofNullable(equipeVainqueure); }

    public int points() {
      return pointsEnSuspens + bonus;
    }
  }
}
