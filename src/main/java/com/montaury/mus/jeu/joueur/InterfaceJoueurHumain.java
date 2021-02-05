package com.montaury.mus.jeu.joueur;

import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.tour.phases.dialogue.Choix;
import com.montaury.mus.jeu.tour.phases.dialogue.Gehiago;
import com.montaury.mus.jeu.tour.phases.dialogue.Hordago;
import com.montaury.mus.jeu.tour.phases.dialogue.Idoki;
import com.montaury.mus.jeu.tour.phases.dialogue.Imido;
import com.montaury.mus.jeu.tour.phases.dialogue.Kanta;
import com.montaury.mus.jeu.tour.phases.dialogue.Paso;
import com.montaury.mus.jeu.tour.phases.dialogue.Tira;
import com.montaury.mus.jeu.tour.phases.dialogue.TypeChoix;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InterfaceJoueurHumain implements InterfaceJoueur {

  private final Scanner scanner = new Scanner(System.in);
  private Main main;

  @Override
  public boolean veutAllerMus() {
    println("Souhaitez-vous aller mus ? (o/n)");
    return scanner.next().equals("o");
  }

  @Override
  public List<Carte> cartesAJeter() {
    println("Veuillez saisir les cartes à jeter (ex: 1,3) :");
    String aJeter = scanner.next();

    // Pour chaque saisie, vérification de sa conformité (cartesAJeterCorrectes())
    while (!cartesAJeterCorrectes(aJeter)) {

      // Tant que la saisie n'est pas correcte on demande de resaisir
      println("Saisie incorrecte, veuillez ressaisir :");
      aJeter = scanner.next();
    }

    return Arrays.stream(aJeter.split(","))
            .mapToInt(Integer::parseInt)
            .mapToObj(indiceCarte -> main.cartesDuPlusGrandAuPlusPetit().get(indiceCarte - 1))
            .collect(Collectors.toList());
  }

  public boolean cartesAJeterCorrectes(String aJeter) {

    boolean saisieCorrecte = true;
    String[] saisieUtilisateur;
    int caseCourante;

    saisieUtilisateur = aJeter.split(",");

    // Si la longueur de la saisie dépasse 4 chiffres (longueur du tableau du split > 4) : la saisie est incorrecte
    if (saisieUtilisateur.length > 4) {
      saisieCorrecte = false;
      println("Attention ! Vous avez inséré trop de cartes. Maximum = 4");
    }

    // Si la longueur de la saisie est égale à 0 chiffres (longueur du tableau du split = 0) : la saisie est incorrecte
    if (saisieUtilisateur.length == 0) {
      saisieCorrecte = false;
      println("Attention ! Vous êtes obligé de jeter au moins une carte.");
    }

    // Pour chaque chiffres (soit chaque carte dans le jeu) saisie
    for (int i = 0; i < saisieUtilisateur.length; i++) {

      // Tentative de conversion de la saisie en string en int
      try {
        caseCourante = Integer.parseInt(saisieUtilisateur[i]);
      }
      catch (final NumberFormatException e) {
        // Si erreur alors, renvoie d'un message d'erreur descriptif
        saisieCorrecte = false;
        println("Attention ! Votre valeur n°" + (i+1) + " (" + saisieUtilisateur[i] + ") " + " doit être une valeur entière.");
        break;
      }

      // Si le chiffre (la carte) n'est pas compris entre 1 et 4 (inclus) alors, renvoie d'un message d'erreur descriptif
      if (caseCourante < 1 || caseCourante > 4) {
        saisieCorrecte = false;
        println("Attention ! Votre carte n°" + (i+1) + " (" + saisieUtilisateur[i] + ") " + " doit être une valeur comprise entre 1 et 4.");
      }
    }

    return saisieCorrecte;
  }

  @Override
  public Choix faireChoixParmi(List<TypeChoix> choixPossibles) {
    print("Faites un choix entre (en toutes lettres): ");
    println(choixPossibles.stream().map(TypeChoix::nom).collect(Collectors.joining(" | ")));
    String choix = scanner.next();
    if (choix.equalsIgnoreCase("Paso")) return new Paso();
    if (choix.equalsIgnoreCase("Imido")) return new Imido();
    if (choix.equalsIgnoreCase("Hordago")) return new Hordago();
    if (choix.equalsIgnoreCase("Idoki")) return new Idoki();
    if (choix.equalsIgnoreCase("Tira")) return new Tira();
    if (choix.equalsIgnoreCase("Gehiago")) return new Gehiago(1);
    if (choix.equalsIgnoreCase("Kanta")) return new Kanta();
    return new Paso();
  }

  @Override
  public void nouvelleMain(Main main) {
    this.main = main;
  }

  private void println(String string) {
    System.out.println(string);
  }

  private void print(String string) {
    System.out.print(string);
  }
}
