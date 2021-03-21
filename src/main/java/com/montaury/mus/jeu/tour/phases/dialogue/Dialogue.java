package com.montaury.mus.jeu.tour.phases.dialogue;

import com.montaury.mus.jeu.joueur.AffichageEvenementsDeJeu;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.montaury.mus.jeu.tour.phases.dialogue.TypeChoix.*;

public class Dialogue {
  private final List<ChoixJoueur> choix = new ArrayList<>();

  public final DialogueTermine derouler(AffichageEvenementsDeJeu affichage, Opposants opposants) {
    Iterator<Joueur> iteratorJoueur = opposants.itererDansLOrdre();
    do {
      Joueur parlant = iteratorJoueur.next();
      if(choix.size() >= 1){
        if(dernierChoix().est(IDOKI)){
          parlant = iteratorJoueur.next();
        }
      }
      Choix choixJoueur = parlant.interfaceJoueur.faireChoixParmi(prochainsChoixPossibles());
      affichage.choixFait(parlant, choixJoueur);
      ajouter(choixJoueur, parlant);
    }
    while (enCours());
    return new DialogueTermine(choix);
  }

  void ajouter(Choix choix, Joueur joueur) {
    this.choix.add(new ChoixJoueur(choix, joueur));
  }

  boolean enCours() {
    if (choix.size() >= 2) {
      if (dernierChoix().est(IDOKI) && avantDernierChoix().est(IDOKI)) return false;
      if (choix.size() >= 4) {
        if (quatreDerniersChoixSontPaso()) return false;
      }
      if (dernierChoix().est(TIRA)) return false;
      if (avantDernierChoix().est(HORDAGO) && (dernierChoix().est(KANTA) || dernierChoix().est(TIRA))) return false;
      if (dernierChoix().metFinAuDialogue()) return false;
    }
    return true;
  }

  private Choix dernierChoix() {
    return choix.get(choix.size() - 1).choix;
  }
  private Choix avantDernierChoix() {
    return choix.get(choix.size() - 2).choix;
  }

  private boolean quatreDerniersChoixSontPaso() {
    if (!choix.get(choix.size()-4).choix.est(PASO)) return false;
    if (!choix.get(choix.size()-3).choix.est(PASO)) return false;
    if (!choix.get(choix.size()-2).choix.est(PASO)) return false;
    if (!choix.get(choix.size()-1).choix.est(PASO)) return false;
    return true;
  }

  private List<TypeChoix> prochainsChoixPossibles() {
    return choix.isEmpty() ? TypeChoix.INITIAUX : dernierChoix().prochainsChoixPossibles();
  }

  public static class ChoixJoueur {
    public final Choix choix;
    public final Joueur joueur;

    public ChoixJoueur(Choix choix, Joueur joueur) {
      this.choix = choix;
      this.joueur = joueur;
    }

    public int mise() {
      return choix.mise();
    }
  }
}
