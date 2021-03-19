package com.montaury.mus.jeu;

import com.montaury.mus.jeu.joueur.*;
import com.montaury.mus.jeu.tour.phases.dialogue.Hordago;
import com.montaury.mus.jeu.tour.phases.dialogue.Kanta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PartieTest {

  @BeforeEach
  void setUp() {
    interfaceJoueurEsku = mock(InterfaceJoueur.class);
    interfaceJoueurPriorite2 = mock(InterfaceJoueur.class);
    interfaceJoueurPriorite3 = mock(InterfaceJoueur.class);
    interfaceJoueurZaku = mock(InterfaceJoueur.class);
    Joueur joueurEsku = new Joueur("J1", interfaceJoueurEsku);
    Joueur joueurPriorite2 = new Joueur("J2", interfaceJoueurPriorite2);
    Joueur joueurPriorite3 = new Joueur("J3", interfaceJoueurPriorite3);
    Joueur joueurZaku = new Joueur("J4", interfaceJoueurZaku);
    Equipe equipe1 = new Equipe(joueurEsku, joueurPriorite3, "E1");
    Equipe equipe2 = new Equipe(joueurPriorite2, joueurZaku, "E2");
    opposants = new Opposants(joueurEsku, joueurPriorite2, joueurPriorite3, joueurZaku);
    partie = new Partie(mock(AffichageEvenementsDeJeu.class));
  }

  @Test
  void devrait_faire_gagner_le_premier_joueur_a_3_manches() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Hordago(), new Kanta());
    when(interfaceJoueurPriorite2.faireChoixParmi(any())).thenReturn(new Hordago(), new Kanta());
    when(interfaceJoueurPriorite3.faireChoixParmi(any())).thenReturn(new Hordago(), new Kanta());
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Hordago(), new Kanta());

    Partie.Resultat resultat = partie.jouer(opposants);

    assertThat(resultat.equipeVainqueure()).isNotNull();
    assertThat(resultat.score().resultatManches()).hasSizeGreaterThanOrEqualTo(3);
  }

  private InterfaceJoueur interfaceJoueurEsku;
  private InterfaceJoueur interfaceJoueurPriorite2;
  private InterfaceJoueur interfaceJoueurPriorite3;
  private InterfaceJoueur interfaceJoueurZaku;
  private Opposants opposants;
  private Partie partie;
}