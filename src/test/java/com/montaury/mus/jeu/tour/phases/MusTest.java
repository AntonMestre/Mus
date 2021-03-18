package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.carte.Defausse;
import com.montaury.mus.jeu.joueur.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.montaury.mus.jeu.carte.Fixtures.paquetEntierCroissant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MusTest {

  @BeforeEach
  void setUp() {
    defausse = new Defausse();
    mus = new Mus(paquetEntierCroissant(), defausse, new AffichageConsoleEvenementsDeJeu(joueurEsku));
    interfaceJoueurEsku = mock(InterfaceJoueur.class);
    interfaceJoueurPriorite2 = mock(InterfaceJoueur.class);
    interfaceJoueurPriorite3 = mock(InterfaceJoueur.class);
    interfaceJoueurZaku = mock(InterfaceJoueur.class);
    joueurEsku = new Joueur("J1", interfaceJoueurEsku);
    joueurEsku = new Joueur("J2", interfaceJoueurPriorite2);
    joueurZaku = new Joueur("J3", interfaceJoueurPriorite3);
    joueurZaku = new Joueur("J4", interfaceJoueurZaku);
    opposants = new Opposants(joueurEsku, joueurPriorite2, joueurPriorite3, joueurZaku);
  }

  @Test
  void devrait_distribuer_quatre_cartes_a_chaque_joueur() {
    when(interfaceJoueurEsku.veutAllerMus()).thenReturn(false);

    mus.jouer(opposants);

    assertThat(joueurEsku.main().cartes()).containsExactly(Carte.AS_BATON, Carte.AS_COUPE, Carte.AS_EPEE, Carte.AS_PIECE);
    assertThat(joueurPriorite2.main().cartes()).containsExactly(Carte.DEUX_BATON, Carte.DEUX_COUPE, Carte.DEUX_EPEE, Carte.DEUX_PIECE);
    assertThat(joueurPriorite3.main().cartes()).containsExactly(Carte.TROIS_BATON, Carte.TROIS_COUPE, Carte.TROIS_EPEE, Carte.TROIS_PIECE);
    assertThat(joueurZaku.main().cartes()).containsExactly(Carte.QUATRE_BATON, Carte.QUATRE_COUPE, Carte.QUATRE_EPEE, Carte.QUATRE_PIECE);
  }

  @Test
  void devrait_se_terminer_si_le_joueur_esku_veut_sortir() {
    when(interfaceJoueurEsku.veutAllerMus()).thenReturn(false);

    mus.jouer(opposants);

    verify(interfaceJoueurPriorite2, times(0)).veutAllerMus();
  }

  @Test
  void devrait_se_terminer_si_le_joueur_priorite_2_veut_sortir() {
    when(interfaceJoueurEsku.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurPriorite2.veutAllerMus()).thenReturn(false);

    mus.jouer(opposants);

    verify(interfaceJoueurPriorite3, times(0)).cartesAJeter();
  }

  @Test
  void devrait_demander_les_cartes_a_jeter_aux_joueurs_s_ils_vont_mus() {
    when(interfaceJoueurEsku.veutAllerMus()).thenReturn(true, false);
    when(interfaceJoueurPriorite2.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurPriorite3.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurZaku.veutAllerMus()).thenReturn(true);

    mus.jouer(opposants);

    verify(interfaceJoueurEsku, times(1)).cartesAJeter();
    verify(interfaceJoueurPriorite2, times(1)).cartesAJeter();
    verify(interfaceJoueurPriorite3, times(1)).cartesAJeter();
    verify(interfaceJoueurZaku, times(1)).cartesAJeter();
  }

  @Test
  void devrait_defausser_les_cartes_a_jeter_si_les_joueurs_vont_mus() {
    when(interfaceJoueurEsku.veutAllerMus()).thenReturn(true, false);
    when(interfaceJoueurEsku.cartesAJeter()).thenReturn(List.of(Carte.AS_COUPE));

    when(interfaceJoueurPriorite2.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurPriorite2.cartesAJeter()).thenReturn(List.of(Carte.DEUX_COUPE));

    when(interfaceJoueurPriorite3.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurPriorite3.cartesAJeter()).thenReturn(List.of(Carte.TROIS_COUPE));

    when(interfaceJoueurZaku.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurZaku.cartesAJeter()).thenReturn(List.of(Carte.QUATRE_COUPE));

    mus.jouer(opposants);

    assertThat(defausse.reprendreCartes()).containsExactly(Carte.AS_COUPE, Carte.DEUX_COUPE, Carte.TROIS_COUPE, Carte.QUATRE_COUPE);
  }

  @Test
  void devrait_distribuer_des_cartes_pour_remplacer_les_cartes_jetees_si_les_joueurs_vont_mus() {
    when(interfaceJoueurEsku.veutAllerMus()).thenReturn(true, false);
    when(interfaceJoueurEsku.cartesAJeter()).thenReturn(List.of(Carte.AS_COUPE));

    when(interfaceJoueurPriorite2.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurPriorite2.cartesAJeter()).thenReturn(List.of(Carte.DEUX_COUPE));

    when(interfaceJoueurPriorite3.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurPriorite3.cartesAJeter()).thenReturn(List.of(Carte.TROIS_COUPE));

    when(interfaceJoueurZaku.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurZaku.cartesAJeter()).thenReturn(List.of(Carte.QUATRE_COUPE));

    mus.jouer(opposants);

    assertThat(joueurEsku.main().cartes()).containsExactly(Carte.AS_BATON, Carte.AS_EPEE, Carte.AS_PIECE, Carte.TROIS_BATON);
    assertThat(joueurPriorite2.main().cartes()).containsExactly(Carte.DEUX_BATON, Carte.DEUX_EPEE, Carte.DEUX_PIECE, Carte.TROIS_EPEE);
    assertThat(joueurPriorite3.main().cartes()).containsExactly(Carte.TROIS_BATON, Carte.TROIS_EPEE, Carte.TROIS_PIECE, Carte.TROIS_PIECE);
    assertThat(joueurZaku.main().cartes()).containsExactly(Carte.QUATRE_BATON, Carte.QUATRE_EPEE, Carte.QUATRE_PIECE, Carte.TROIS_COUPE);
  }


  @Test
  void devrait_renvoyer_faux_si_le_joueur_ne_saisit_que_des_virgules() {

    monInterfaceHumain=new InterfaceJoueurHumain();

    String saisieUtilisateur = ",";

    assertThat(monInterfaceHumain.cartesAJeterCorrectes(saisieUtilisateur)).isEqualTo(false);

  }

  @Test
  void devrait_renvoyer_faux_si_le_joueur_fait_une_mauvaise_saisie_autre_q_une_virgule() {

    monInterfaceHumain=new InterfaceJoueurHumain();

    String [] saisieUtilisateur = {"-","d","m","40",",20","1,50","60,1"};

    for(int i=0; i < saisieUtilisateur.length ; i++) assertThat(monInterfaceHumain.cartesAJeterCorrectes(saisieUtilisateur[i])).isEqualTo(false);

  }

  @Test
  void devrait_renvoyer_un_message_demandant_de_ressaisir_les_cartes_a_jeter_si_le_joueur_ne_saisit_qu_une_virgule() {

  }

  private Mus mus;
  private InterfaceJoueur interfaceJoueurEsku;
  private InterfaceJoueur interfaceJoueurPriorite2;
  private InterfaceJoueur interfaceJoueurPriorite3;
  private InterfaceJoueur interfaceJoueurZaku;
  private InterfaceJoueurHumain monInterfaceHumain;
  private Joueur joueurEsku;
  private Joueur joueurPriorite2;
  private Joueur joueurPriorite3;
  private Joueur joueurZaku;
  private Opposants opposants;
  private Defausse defausse;
}