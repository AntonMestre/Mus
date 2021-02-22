package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.carte.ValeurCarte;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Main;
import com.montaury.mus.jeu.joueur.Opposants;
import java.util.List;

import static com.montaury.mus.jeu.carte.ValeurCarte.Comparaison.PLUS_GRANDE;
import static com.montaury.mus.jeu.carte.ValeurCarte.Comparaison.PLUS_PETITE;

public class Grand extends Phase {
  public Grand() {
    super("Grand");
  }

  @Override
  protected Joueur meilleurParmi(Opposants opposants) {

    Joueur joueurEsku = opposants.joueurEsku();
    Joueur joueurZaku = opposants.joueurZaku();

    List<Carte> cartesJoueurEsku = joueurEsku.main().cartesDuPlusGrandAuPlusPetit();
    List<Carte> cartesJoueurZaku = joueurZaku.main().cartesDuPlusGrandAuPlusPetit();

    if(opposants.isJeuEnEquipe()) {

      Joueur joueurPriorite2 = opposants.joueurPriorite2();
      Joueur joueurPriorite3 = opposants.joueurPriorite3();

      List<Carte> cartesjoueurPriorite2 = joueurPriorite2.main().cartesDuPlusGrandAuPlusPetit();
      List<Carte> cartesjoueurPriorite3 = joueurPriorite3.main().cartesDuPlusGrandAuPlusPetit();

      for (int i = 0; i < Main.TAILLE; i++) {

        ValeurCarte.Comparaison compareEskuZaku = cartesJoueurEsku.get(i).comparerAvec(cartesJoueurZaku.get(i));
        ValeurCarte.Comparaison compareEskuPrio2 = cartesJoueurEsku.get(i).comparerAvec(cartesjoueurPriorite2.get(i));
        ValeurCarte.Comparaison compareEskuPrio3 = cartesJoueurEsku.get(i).comparerAvec(cartesjoueurPriorite3.get(i));

        ValeurCarte.Comparaison compareZakuEsku = cartesJoueurZaku.get(i).comparerAvec(cartesJoueurEsku.get(i));
        ValeurCarte.Comparaison compareZakuPrio2 = cartesJoueurZaku.get(i).comparerAvec(cartesjoueurPriorite2.get(i));
        ValeurCarte.Comparaison compareZakuPrio3 = cartesJoueurZaku.get(i).comparerAvec(cartesjoueurPriorite3.get(i));

        ValeurCarte.Comparaison comparePrio2Esku = cartesJoueurEsku.get(i).comparerAvec(cartesJoueurEsku.get(i));
        ValeurCarte.Comparaison comparePrio2Zaku = cartesJoueurEsku.get(i).comparerAvec(cartesJoueurZaku.get(i));
        ValeurCarte.Comparaison comparePrio2Prio3 = cartesJoueurEsku.get(i).comparerAvec(cartesjoueurPriorite3.get(i));

        if(compareEskuZaku == PLUS_GRANDE && compareEskuPrio2 == PLUS_GRANDE && compareEskuPrio3 == PLUS_GRANDE){
          return joueurEsku;
        }else if (compareZakuEsku == PLUS_GRANDE && compareZakuPrio2 == PLUS_GRANDE && compareZakuPrio3 == PLUS_GRANDE){
          return joueurZaku;
        }else if (comparePrio2Esku == PLUS_GRANDE && comparePrio2Zaku == PLUS_GRANDE && comparePrio2Prio3 == PLUS_GRANDE){
          return joueurPriorite2;
        }else{
          return joueurPriorite3;
        }
      }

    }else {

      for (int i = 0; i < Main.TAILLE; i++) {
        ValeurCarte.Comparaison compare = cartesJoueurEsku.get(i).comparerAvec(cartesJoueurZaku.get(i));
        if (compare == PLUS_GRANDE) {
          return joueurEsku;
        }
        if (compare == PLUS_PETITE) {
          return joueurZaku;
        }
      }

    }

    return joueurEsku;
  }
}
