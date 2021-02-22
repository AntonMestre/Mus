package com.montaury.mus;

import com.montaury.mus.jeu.Partie;
import com.montaury.mus.jeu.joueur.AffichageConsoleEvenementsDeJeu;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;

import java.util.Scanner;

public class Mus {
  public static void main(String[] args) {

    // On récupère le choix du mode de jeu
    int choixModeDeJeu = choixModeJeu();

    // On récupère le nom du joueur et on lui crée un objet humain
    System.out.print("Entrez votre nom: ");
    String nomJoueur = new Scanner(System.in).next();
    Joueur humain = Joueur.humain(nomJoueur);

    // Lancement de la partie en fonction du mode de jeu
    if (choixModeDeJeu == 1) {
      Partie partie = new Partie(new AffichageConsoleEvenementsDeJeu(humain));
      Partie.Resultat resultat = partie.jouer(new Opposants(humain, Joueur.ordinateur()));

      System.out.println("Le vainqueur de la partie est " + resultat.vainqueur().nom());
    }

    if (choixModeDeJeu == 2) {
      Partie partie = new Partie(new AffichageConsoleEvenementsDeJeu(humain));
      Partie.Resultat resultat = partie.jouer(new Opposants(humain, Joueur.ordinateur(), Joueur.ordinateur(), Joueur.ordinateur()));

      System.out.println("Le vainqueur de la partie est " + resultat.vainqueur().nom());
    }

    else {
      System.out.println("WTF");
    }
  }

  private static int choixModeJeu() {
    // On affiche le menu
    System.out.println("Choisissez votre mode de jeu :");
    System.out.println("\t1 - 1c1 Adversaire IA");
    System.out.println("\t2 - 2v2 Adversaire et Equipier IA");
    String choixModeDeJeu;

    while (true) {
      // On récupère le choix du joueur
      choixModeDeJeu = new Scanner(System.in).next();

      // Si le joueur n'a pas saisit 1 ou 2
      if (!choixModeDeJeu.equals("1") && !choixModeDeJeu.equals("2")) {
        System.out.println("Saisie incorrecte, choisissez entre '1' et '2'");
      }
      // Sinon on sort de la boucle
      else {
        break;
      }
    }
    return Integer.parseInt(choixModeDeJeu);
  }
}

