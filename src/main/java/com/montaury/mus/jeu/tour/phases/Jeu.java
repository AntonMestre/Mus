package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;
import java.util.List;

public class Jeu extends Phase {

  private static final List<Integer> RANG_JEUX = List.of(31, 32, 40, 37, 36, 35, 34, 33);

  public Jeu() {
    super("Jeu");
  }

  public static boolean aLeJeu(Joueur joueur) {
    return RANG_JEUX.contains(joueur.main().pointsPourJeu());
  }

  @Override
  protected boolean peutParticiper(Joueur joueur) {
    return aLeJeu(joueur);
  }

  @Override
  protected Joueur meilleurParmi(Opposants opposants) {

    if(opposants.isJeuEnEquipe()){

      boolean comparaisonEskuZaku=rangDuJeu(opposants.joueurEsku()) <= rangDuJeu(opposants.joueurZaku());
      boolean comparaisonPrio2Prio3=rangDuJeu(opposants.joueurPriorite2()) <= rangDuJeu(opposants.joueurPriorite3());

      if(comparaisonEskuZaku && comparaisonPrio2Prio3){
        return rangDuJeu(opposants.joueurEsku()) <= rangDuJeu(opposants.joueurPriorite2()) ?opposants.joueurEsku():opposants.joueurPriorite2();
      }else if(comparaisonEskuZaku && !comparaisonPrio2Prio3){
        return rangDuJeu(opposants.joueurEsku()) <= rangDuJeu(opposants.joueurPriorite3()) ?opposants.joueurEsku():opposants.joueurPriorite3();
      }else if(!comparaisonEskuZaku && comparaisonPrio2Prio3){
        return rangDuJeu(opposants.joueurZaku()) <= rangDuJeu(opposants.joueurPriorite2()) ?opposants.joueurZaku():opposants.joueurPriorite2();
      }else if(!comparaisonEskuZaku && !comparaisonPrio2Prio3){
        return rangDuJeu(opposants.joueurZaku()) <= rangDuJeu(opposants.joueurPriorite3()) ?opposants.joueurZaku():opposants.joueurPriorite3();
      }

    }else {
      return rangDuJeu(opposants.joueurEsku()) <= rangDuJeu(opposants.joueurZaku()) ?
              opposants.joueurEsku() :
              opposants.joueurZaku();
    }

    return opposants.joueurEsku();
  }

  private int rangDuJeu(Joueur joueur) {
    return RANG_JEUX.indexOf(joueur.main().pointsPourJeu());
  }

  @Override
  public int pointsBonus(Joueur vainqueur) {
    return vainqueur.main().pointsPourJeu() == 31 ? 3 : 2;
  }
}
