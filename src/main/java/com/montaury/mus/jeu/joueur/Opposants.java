package com.montaury.mus.jeu.joueur;

import java.util.Iterator;
import java.util.List;

public class Opposants {
  private Joueur joueurEsku;
  private Joueur joueurPriorite2;
  private Joueur joueurPriorite3;
  private Joueur joueurZaku;

  private boolean jeuEnEquipe;

  public Opposants(Joueur joueurEsku, Joueur joueurZaku) {
    this.joueurEsku = joueurEsku;
    this.joueurZaku = joueurZaku;

    this.jeuEnEquipe = false;
  }

  public Opposants(Joueur joueurEsku, Joueur joueurPriorite2, Joueur joueurPriorite3, Joueur joueurZaku) {
    this.joueurEsku = joueurEsku;
    this.joueurPriorite2 = joueurPriorite2;
    this.joueurPriorite3 = joueurPriorite3;
    this.joueurZaku = joueurZaku;

    this.jeuEnEquipe = true;
  }

  public void tourner() {
    if (jeuEnEquipe) {
      Joueur tmp = joueurEsku;
      joueurEsku = joueurPriorite2;
      joueurPriorite2 = joueurPriorite3;
      joueurPriorite3 = joueurZaku;
      joueurZaku = tmp;
    }

    else {
      Joueur tmp = joueurEsku;
      joueurEsku = joueurZaku;
      joueurZaku = tmp;
    }

  }

  public Joueur joueurEsku() {
    return joueurEsku;
  }
  public Joueur joueurPriorite2() { return joueurPriorite2; }
  public Joueur joueurPriorite3() { return joueurPriorite3; }
  public Joueur joueurZaku() {
    return joueurZaku;
  }
  public boolean isJeuEnEquipe() { return jeuEnEquipe; }

  public Iterator<Joueur> itererDansLOrdre() {
    return new IteratorInfini(this);
  }

  public List<Joueur> dansLOrdre() {
    if (jeuEnEquipe) {
      return List.of(joueurEsku, joueurPriorite2, joueurPriorite3, joueurZaku);
    }

    else {
      return List.of(joueurEsku, joueurZaku);
    }
  }

  private static class IteratorInfini implements Iterator<Joueur> {
    private final Opposants opposants;
    private Joueur suivant;

    public IteratorInfini(Opposants opposants) {
      this.opposants = opposants;
      suivant = opposants.joueurEsku;
    }

    @Override
    public boolean hasNext() {
      return true;
    }

    @Override
    public Joueur next() {
      Joueur next = suivant;

      if (opposants.jeuEnEquipe) {
        if (suivant == opposants.joueurEsku) {
          suivant = opposants.joueurPriorite2;
        }

        if (suivant == opposants.joueurPriorite2) {
          suivant = opposants.joueurPriorite3;
        }

        if (suivant == opposants.joueurPriorite3) {
          suivant = opposants.joueurZaku;
        }

        if (suivant == opposants.joueurZaku) {
          suivant = opposants.joueurEsku;
        }
      }

      else {
        suivant = suivant == opposants.joueurEsku ? opposants.joueurZaku : opposants.joueurEsku;
      }

      return next;
    }
  }
}
