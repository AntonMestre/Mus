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
    String[] saisieUtilisateur;
    int caseCourante = 0;
    boolean saisieCorrecte = false;
    boolean estEntier = false;

    println("Veuillez saisir les cartes à jeter (ex: 1,3) :");
    String aJeter = scanner.next();

    while (true) {
      saisieUtilisateur = aJeter.split(",");

      if (saisieUtilisateur.length <= 4) {
        for (int i = 0; i < saisieUtilisateur.length; i++) {

          try {
            estEntier = true;
            caseCourante = Integer.parseInt(saisieUtilisateur[i]);

          } catch (final NumberFormatException e) {
            estEntier = false;
          }

          if ((caseCourante >= 1 && caseCourante <= 4) && estEntier) {
            saisieCorrecte = true;
          }
          else {
            saisieCorrecte = false;
          }
        }
      }

      if (!saisieCorrecte) {
        break;
      }

      println("Saisie incorrecte, veuillez ressaisir :");
      aJeter = scanner.next();
    }

    return Arrays.stream(aJeter.split(","))
            .mapToInt(Integer::parseInt)
            .mapToObj(indiceCarte -> main.cartesDuPlusGrandAuPlusPetit().get(indiceCarte - 1))
            .collect(Collectors.toList());
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
