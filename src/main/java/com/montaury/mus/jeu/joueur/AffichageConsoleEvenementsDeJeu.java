package com.montaury.mus.jeu.joueur;

import com.montaury.mus.jeu.Manche;
import com.montaury.mus.jeu.Partie;
import com.montaury.mus.jeu.tour.phases.Phase;
import com.montaury.mus.jeu.tour.phases.dialogue.Choix;
import java.util.stream.Collectors;

public class AffichageConsoleEvenementsDeJeu implements AffichageEvenementsDeJeu {
  private final Joueur joueurCourant;

  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";

  public AffichageConsoleEvenementsDeJeu(Joueur courant) {
    this.joueurCourant = courant;
  }

  @Override
  public void nouvellePartie() {
    println(ANSI_PURPLE + "Nouvelle partie" + ANSI_RESET);
  }

  @Override
  public void nouvelleManche() {
    println(ANSI_CYAN + "Nouvelle manche" + ANSI_RESET);
  }

  @Override
  public void mancheTerminee(Partie.Score score) {
    println(ANSI_CYAN + "Manche terminée" + ANSI_RESET);

    score.resultatManches().forEach(manche -> println("Vainqueur : " + manche.equipeVainqueure().nom() + ", score du perdant : " + manche.pointsVaincu()));
  }

  @Override
  public void nouveauTour(Opposants opposants) {
    println(opposants.joueurEsku().nom() + " est esku");
  }

  @Override
  public void tourTermine(Opposants opposants, Manche.Score score) {
    println("Tour terminé");
    opposants.dansLOrdre().forEach(this::afficherMain);

    score.scoreParEquipe().forEach((key, value) -> println("Score " + key.nom() + ": " + value));

    println();
  }

  @Override
  public void choixFait(Joueur joueur, Choix choix) {
    println(joueur.nom() + ": " + description(choix));
  }

  private static String description(Choix choix) {
    return choix.type().nom();
  }

  @Override
  public void nouvelleMain(Joueur joueur) {
    if (joueur == joueurCourant) {
      println("Votre main est: ");
      afficherMain(joueur);
      println();
    }
  }

  private void afficherMain(Joueur joueur) {
    println(joueur.nom() + ": " + joueur.main().cartesDuPlusGrandAuPlusPetit().stream().map(carte -> carte.valeur() + " " + carte.type()).collect(Collectors.joining(" | ")));
  }

  @Override
  public void nouvellePhase(Phase phase) {
    println(ANSI_RED + "Nouvelle phase: " + phase.nom() + ANSI_RESET);
  }

  @Override
  public void finPhase(Phase phase,Phase.Resultat resultat) {
    println("Résultat de la phase: equipe vainqueure -> " + resultat.equipeVainqueure().get().nom());
  }

  @Override
  public void partieTerminee(Partie.Resultat resultat) {
    println("La partie est terminée !");

    println("Vainqueur: " + resultat.equipeVainqueure());

  }

  private void println(String string) {
    System.out.println(string);
  }

  private void println() {
    System.out.println();
  }
}
