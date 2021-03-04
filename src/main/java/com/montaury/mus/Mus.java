package com.montaury.mus;

import com.montaury.mus.jeu.Partie;
import com.montaury.mus.jeu.joueur.AffichageConsoleEvenementsDeJeu;
import com.montaury.mus.jeu.joueur.Equipe;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;

import java.util.Scanner;

public class Mus {
  public static void main(String[] args) {
    // On récupère le nom du joueur et on lui crée un Joueur humain
    System.out.print("Entrez votre nom : ");
    String nomJoueur = new Scanner(System.in).next();
    Joueur humain = Joueur.humain(nomJoueur);

    // Création d'un adversaire customisé
    Joueur ordinateur1 = Joueur.ordinateur("Adversaire 1");

    // Création d'adversaires supplémentaires customisés
    Joueur ordinateur2 = Joueur.ordinateur("Coequipier");
    Joueur ordinateur3 = Joueur.ordinateur("Adversaire 2");

    // On demande au joueur le nom de l'équipe souhaitée
    System.out.print("Choisissez un nom pour votre equipe : ");
    String nomEquipeJoueur = new Scanner(System.in).next();

    // Inscription des équipes
    Equipe equipeJoueur = new Equipe(humain, ordinateur2, nomEquipeJoueur);
    Equipe equipeOrdi = new Equipe(ordinateur1, ordinateur3, "Team IA");


    Partie partie = new Partie(new AffichageConsoleEvenementsDeJeu(humain));
    Partie.Resultat resultat = partie.jouer(new Opposants(humain, ordinateur1,ordinateur2,ordinateur3));

    System.out.println("Le vainqueur de la partie est " + resultat.equipeVainqueure().nom());
  }
}

