package com.montaury.mus.jeu;

import com.montaury.mus.jeu.joueur.AffichageEvenementsDeJeu;
import com.montaury.mus.jeu.joueur.InterfaceJoueur;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;
import com.montaury.mus.jeu.tour.phases.dialogue.Gehiago;
import com.montaury.mus.jeu.tour.phases.dialogue.Hordago;
import com.montaury.mus.jeu.tour.phases.dialogue.Imido;
import com.montaury.mus.jeu.tour.phases.dialogue.Kanta;
import com.montaury.mus.jeu.tour.phases.dialogue.Paso;
import com.montaury.mus.jeu.tour.phases.dialogue.Tira;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MancheTest {

  private Manche manche;

  @BeforeEach
  void setUp() {
    interfaceJoueurEsku = mock(InterfaceJoueur.class);
    interfaceJoueurPriorite2 = mock(InterfaceJoueur.class);
    interfaceJoueurPriorite3 = mock(InterfaceJoueur.class);
    interfaceJoueurZaku = mock(InterfaceJoueur.class);
    joueurEsku = new Joueur("J1", interfaceJoueurEsku);
    joueurEsku = new Joueur("J2", interfaceJoueurPriorite2);
    joueurZaku = new Joueur("J3", interfaceJoueurPriorite3);
    joueurZaku = new Joueur("J4", interfaceJoueurZaku);
    opposants = new Opposants(joueurEsku, joueurPriorite2, joueurPriorite3, joueurZaku);
    manche = new Manche(mock(AffichageEvenementsDeJeu.class));
  }

  @Test
  void devrait_terminer_la_manche_si_hordago_au_grand() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Hordago());
    when(interfaceJoueurPriorite2.faireChoixParmi(any())).thenReturn(new Kanta());

    Manche.Resultat resultat = manche.jouer(opposants);

    assertThat(resultat.equipeVainqueure()).isNotNull();
    assertThat(resultat.pointsVaincu()).isZero();
  }

  @Test
  void devrait_terminer_la_manche_si_un_joueur_a_40_points() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Imido(), new Gehiago(2));
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Gehiago(40), new Tira());

    Manche.Resultat resultat = manche.jouer(opposants);

    assertThat(resultat.equipeVainqueure()).isEqualTo(joueurEsku);
    assertThat(resultat.pointsVaincu()).isZero();
  }

  @Test
  void devrait_changer_l_ordre_des_opposants_a_la_fin_du_tour() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Hordago());
    when(interfaceJoueurPriorite2.faireChoixParmi(any())).thenReturn(new Kanta());

    manche.jouer(opposants);

    assertThat(opposants.dansLOrdre()).containsExactly(joueurPriorite2, joueurPriorite3, joueurZaku, joueurEsku);
  }

  private InterfaceJoueur interfaceJoueurEsku;
  private InterfaceJoueur interfaceJoueurPriorite2;
  private InterfaceJoueur interfaceJoueurPriorite3;
  private InterfaceJoueur interfaceJoueurZaku;
  private Joueur joueurEsku;
  private Joueur joueurPriorite2;
  private Joueur joueurPriorite3;
  private Joueur joueurZaku;
  private Opposants opposants;

}