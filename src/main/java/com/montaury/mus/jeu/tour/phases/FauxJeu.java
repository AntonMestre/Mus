package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;

import static com.montaury.mus.jeu.tour.phases.Jeu.aLeJeu;

public class FauxJeu extends Phase {
  public FauxJeu() {
    super("Faux Jeu");
  }

  @Override
  protected boolean peutParticiper(Joueur joueur) {
    return !aLeJeu(joueur);
  }

  @Override
  protected Joueur meilleurParmi(Opposants opposants) {

    int pointsJoueurEsku = opposants.joueurEsku().main().pointsPourJeu();
    int pointsJoueurZaku = opposants.joueurZaku().main().pointsPourJeu();

    if(opposants.jouentEnEquipe()){
      int pointsJoueurPrio2 = opposants.joueurPriorite2().main().pointsPourJeu();
      int pointsJoueurPrio3 = opposants.joueurPriorite3().main().pointsPourJeu();

      boolean comparaisonEskuZaku=pointsJoueurEsku >= pointsJoueurZaku;
      boolean comparaisonPrio2Prio3=pointsJoueurPrio2 >= pointsJoueurPrio3;

      if(comparaisonEskuZaku && comparaisonPrio2Prio3){
        return pointsJoueurEsku >= pointsJoueurPrio2 ? opposants.joueurEsku() : opposants.joueurPriorite2();
      }else if(comparaisonEskuZaku && !comparaisonPrio2Prio3){
        return pointsJoueurEsku >= pointsJoueurPrio3 ? opposants.joueurEsku() : opposants.joueurPriorite3();
      }else if(!comparaisonEskuZaku && comparaisonPrio2Prio3){
        return pointsJoueurZaku >= pointsJoueurPrio2 ? opposants.joueurZaku() : opposants.joueurPriorite2();
      }else if(!comparaisonEskuZaku && !comparaisonPrio2Prio3){
        return pointsJoueurZaku >= pointsJoueurPrio3 ? opposants.joueurZaku() : opposants.joueurPriorite3();
      }

    }else{
      return pointsJoueurEsku >= pointsJoueurZaku ? opposants.joueurEsku() : opposants.joueurZaku();
    }

    return opposants.joueurEsku();

  }

  @Override
  public int pointsBonus(Joueur vainqueur) {
    return 1;
  }
}
