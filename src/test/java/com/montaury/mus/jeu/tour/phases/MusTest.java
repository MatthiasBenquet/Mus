package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.carte.Defausse;
import com.montaury.mus.jeu.joueur.AffichageConsoleEvenementsDeJeu;
import com.montaury.mus.jeu.joueur.InterfaceJoueur;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;
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
    interfaceJoueurDeux = mock(InterfaceJoueur.class);
    interfaceJoueurTrois = mock(InterfaceJoueur.class);
    interfaceJoueurZaku = mock(InterfaceJoueur.class);
    joueurEsku = new Joueur("J1", interfaceJoueurEsku);
    joueurDeux = new Joueur("J2", interfaceJoueurDeux);
    joueurTrois = new Joueur("J3", interfaceJoueurTrois);
    joueurZaku = new Joueur("J4", interfaceJoueurZaku);
    opposants = new Opposants(joueurEsku, joueurDeux, joueurTrois, joueurZaku);
  }

  @Test
  void devrait_distribuer_quatre_cartes_a_chaque_joueur() {
    when(interfaceJoueurEsku.veutAllerMus()).thenReturn(false);

    mus.jouer(opposants);

    assertThat(joueurEsku.main().cartes()).containsExactly(Carte.AS_BATON, Carte.AS_COUPE, Carte.AS_EPEE, Carte.AS_PIECE);
    assertThat(joueurDeux.main().cartes()).containsExactly(Carte.DEUX_BATON, Carte.DEUX_COUPE, Carte.DEUX_EPEE, Carte.DEUX_PIECE);
    assertThat(joueurTrois.main().cartes()).containsExactly(Carte.TROIS_BATON, Carte.TROIS_COUPE, Carte.TROIS_EPEE, Carte.TROIS_PIECE);
    assertThat(joueurZaku.main().cartes()).containsExactly(Carte.QUATRE_BATON, Carte.QUATRE_COUPE, Carte.QUATRE_EPEE, Carte.QUATRE_PIECE);
  }

  @Test
  void devrait_se_terminer_si_le_joueur_esku_veut_sortir() {
    when(interfaceJoueurEsku.veutAllerMus()).thenReturn(false);

    mus.jouer(opposants);

    verify(interfaceJoueurZaku, times(0)).veutAllerMus();
  }

  @Test
  void devrait_se_terminer_si_le_joueur_deux_veut_sortir() {
    when(interfaceJoueurEsku.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurDeux.veutAllerMus()).thenReturn(false);

    mus.jouer(opposants);

    verify(interfaceJoueurTrois, times(0)).veutAllerMus();
    verify(interfaceJoueurZaku, times(0)).veutAllerMus();
  }

  @Test
  void devrait_se_terminer_si_le_joueur_trois_veut_sortir() {
    when(interfaceJoueurEsku.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurDeux.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurTrois .veutAllerMus()).thenReturn(false);

    mus.jouer(opposants);

    verify(interfaceJoueurZaku, times(0)).veutAllerMus();
  }

  @Test
  void devrait_se_terminer_si_le_joueur_zaku_veut_sortir() {
    when(interfaceJoueurEsku.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurDeux.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurTrois.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurZaku.veutAllerMus()).thenReturn(false);

    mus.jouer(opposants);

    verify(interfaceJoueurEsku, times(0)).cartesAJeter();
  }

  @Test
  void devrait_demander_les_cartes_a_jeter_aux_joueurs_s_ils_vont_mus() {
    when(interfaceJoueurEsku.veutAllerMus()).thenReturn(true, false);
    when(interfaceJoueurDeux.veutAllerMus()).thenReturn(true, false);
    when(interfaceJoueurTrois.veutAllerMus()).thenReturn(true, false);
    when(interfaceJoueurZaku.veutAllerMus()).thenReturn(true);

    mus.jouer(opposants);

    verify(interfaceJoueurEsku, times(1)).cartesAJeter();
    verify(interfaceJoueurZaku, times(1)).cartesAJeter();
  }

  @Test
  void devrait_defausser_les_cartes_a_jeter_si_les_joueurs_vont_mus() {
    when(interfaceJoueurEsku.veutAllerMus()).thenReturn(true, false);
    when(interfaceJoueurEsku.cartesAJeter()).thenReturn(List.of(Carte.AS_COUPE));
    when(interfaceJoueurDeux.veutAllerMus()).thenReturn(true, false);
    when(interfaceJoueurDeux.cartesAJeter()).thenReturn(List.of(Carte.DEUX_COUPE));
    when(interfaceJoueurTrois.veutAllerMus()).thenReturn(true, false);
    when(interfaceJoueurTrois.cartesAJeter()).thenReturn(List.of(Carte.TROIS_COUPE));
    when(interfaceJoueurZaku.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurZaku.cartesAJeter()).thenReturn(List.of(Carte.QUATRE_COUPE));

    mus.jouer(opposants);

    assertThat(defausse.reprendreCartes()).containsExactly(Carte.AS_COUPE, Carte.DEUX_COUPE, Carte.TROIS_COUPE, Carte.QUATRE_COUPE);
  }

  @Test
  void devrait_distribuer_des_cartes_pour_remplacer_les_cartes_jetees_si_les_joueurs_vont_mus() {
    when(interfaceJoueurEsku.veutAllerMus()).thenReturn(true, false);
    when(interfaceJoueurEsku.cartesAJeter()).thenReturn(List.of(Carte.AS_COUPE));
    when(interfaceJoueurDeux.veutAllerMus()).thenReturn(true, false);
    when(interfaceJoueurDeux.cartesAJeter()).thenReturn(List.of(Carte.DEUX_COUPE));
    when(interfaceJoueurTrois.veutAllerMus()).thenReturn(true, false);
    when(interfaceJoueurTrois.cartesAJeter()).thenReturn(List.of(Carte.TROIS_COUPE));
    when(interfaceJoueurZaku.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurZaku.cartesAJeter()).thenReturn(List.of(Carte.QUATRE_COUPE));

    mus.jouer(opposants);

    assertThat(joueurEsku.main().cartes()).containsExactly(Carte.AS_BATON, Carte.AS_EPEE, Carte.AS_PIECE, Carte.CINQ_BATON);
    assertThat(joueurDeux.main().cartes()).containsExactly(Carte.DEUX_BATON, Carte.DEUX_EPEE, Carte.DEUX_PIECE, Carte.CINQ_COUPE);
    assertThat(joueurTrois.main().cartes()).containsExactly(Carte.TROIS_BATON, Carte.TROIS_EPEE, Carte.TROIS_PIECE, Carte.CINQ_EPEE);
    assertThat(joueurZaku.main().cartes()).containsExactly(Carte.QUATRE_BATON, Carte.QUATRE_EPEE, Carte.QUATRE_PIECE, Carte.CINQ_PIECE);
  }

  private Mus mus;
  private InterfaceJoueur interfaceJoueurEsku;
  private InterfaceJoueur interfaceJoueurDeux;
  private InterfaceJoueur interfaceJoueurTrois;
  private InterfaceJoueur interfaceJoueurZaku;
  private Joueur joueurEsku;
  private Joueur joueurDeux;
  private Joueur joueurTrois;
  private Joueur joueurZaku;
  private Opposants opposants;
  private Defausse defausse;
}