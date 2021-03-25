package com.montaury.mus.jeu.tour;

import com.montaury.mus.jeu.Manche;
import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.carte.Defausse;
import com.montaury.mus.jeu.joueur.AffichageEvenementsDeJeu;
import com.montaury.mus.jeu.joueur.InterfaceJoueur;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;
import com.montaury.mus.jeu.tour.phases.dialogue.Gehiago;
import com.montaury.mus.jeu.tour.phases.dialogue.Hordago;
import com.montaury.mus.jeu.tour.phases.dialogue.Idoki;
import com.montaury.mus.jeu.tour.phases.dialogue.Imido;
import com.montaury.mus.jeu.tour.phases.dialogue.Kanta;
import com.montaury.mus.jeu.tour.phases.dialogue.Paso;
import com.montaury.mus.jeu.tour.phases.dialogue.Tira;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.montaury.mus.jeu.carte.Fixtures.paquetAvec;
import static com.montaury.mus.jeu.carte.Fixtures.paquetEntierCroissant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TourTest {

  @BeforeEach
  void setUp() {
    interfaceJoueurEsku = mock(InterfaceJoueur.class);
    interfaceJoueurDeux = mock(InterfaceJoueur.class);
    interfaceJoueurTrois = mock(InterfaceJoueur.class);
    interfaceJoueurZaku = mock(InterfaceJoueur.class);
    joueurEsku = new Joueur("J1", interfaceJoueurEsku);
    joueurDeux = new Joueur("J2", interfaceJoueurDeux);
    joueurTrois = new Joueur("J3", interfaceJoueurTrois);
    joueurZaku = new Joueur("J4", interfaceJoueurZaku);
    opposants = new Opposants(joueurEsku,joueurDeux, joueurTrois, joueurZaku);
    score = new Manche.Score(opposants);
    evenementsDeJeu = mock(AffichageEvenementsDeJeu.class);
    tour = new Tour(evenementsDeJeu, paquetEntierCroissant(), new Defausse());
  }

  @Test
  void devrait_donner_tous_les_points_au_joueur_esku_si_les_autres_joueurs_font_tira() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Imido());
    when(interfaceJoueurDeux.faireChoixParmi(any())).thenReturn(new Tira());
    when(interfaceJoueurTrois.faireChoixParmi(any())).thenReturn(new Tira());
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Tira());

    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParEquipe()).containsEntry(joueurEsku.equipe(), 8);
    assertThat(score.scoreParEquipe()).containsEntry(joueurDeux.equipe(), 0);
  }

  @Test
  void devrait_repartir_les_points_si_tout_est_paso() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Paso());
    when(interfaceJoueurDeux.faireChoixParmi(any())).thenReturn(new Paso());
    when(interfaceJoueurTrois.faireChoixParmi(any())).thenReturn(new Paso());
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Paso());

    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParEquipe()).containsEntry(joueurEsku.equipe(), 20);
    assertThat(score.scoreParEquipe()).containsEntry(joueurZaku.equipe(), 20);
  }

  @Test
  void devrait_faire_gagner_le_joueur_deux_si_hordago_au_grand() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Hordago());
    when(interfaceJoueurDeux.faireChoixParmi(any())).thenReturn(new Kanta());
    when(interfaceJoueurTrois.faireChoixParmi(any())).thenReturn(new Kanta());
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Kanta());

    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).contains(joueurDeux.equipe());
    assertThat(score.scoreParEquipe()).containsEntry(joueurEsku.equipe(), 0);
    assertThat(score.scoreParEquipe()).containsEntry(joueurDeux.equipe(), 40);
  }

  @Test
  void devrait_partager_les_points_si_tout_est_idoki() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Imido());
    when(interfaceJoueurDeux.faireChoixParmi(any())).thenReturn(new Idoki());
    when(interfaceJoueurTrois.faireChoixParmi(any())).thenReturn(new Idoki());
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Idoki());

    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParEquipe()).containsEntry(joueurEsku.equipe(), 2);
    assertThat(score.scoreParEquipe()).containsEntry(joueurDeux.equipe(), 10);
  }

  @Test
  void devrait_partager_les_points_si_tout_est_gehiago_puis_idoki() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Imido());
    when(interfaceJoueurDeux.faireChoixParmi(any())).thenReturn(new Gehiago(2));
    when(interfaceJoueurTrois.faireChoixParmi(any())).thenReturn(new Idoki());
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Gehiago(2));

    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParEquipe()).containsEntry(joueurEsku.equipe(), 4);
    assertThat(score.scoreParEquipe()).containsEntry(joueurZaku.equipe(), 20);
  }

  @Test
  void devrait_privilegier_le_joueur_esku_si_les_mains_sont_identiques() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Imido());
    when(interfaceJoueurDeux.faireChoixParmi(any())).thenReturn(new Idoki());
    when(interfaceJoueurTrois.faireChoixParmi(any())).thenReturn(new Idoki());
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Idoki());

    Tour tour = new Tour(evenementsDeJeu, paquetAvec(Carte.AS_BATON, Carte.DEUX_BATON, Carte.TROIS_BATON, Carte.QUATRE_BATON, Carte.AS_COUPE, Carte.DEUX_COUPE, Carte.TROIS_COUPE, Carte.QUATRE_COUPE, Carte.AS_PIECE, Carte.DEUX_PIECE, Carte.TROIS_PIECE, Carte.QUATRE_PIECE, Carte.AS_EPEE, Carte.DEUX_EPEE, Carte.TROIS_EPEE, Carte.QUATRE_EPEE), new Defausse());

    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParEquipe()).containsEntry(joueurEsku.equipe(), 7);
    assertThat(score.scoreParEquipe()).containsEntry(joueurZaku.equipe(), 0);
  }

  private InterfaceJoueur interfaceJoueurEsku;
  private InterfaceJoueur interfaceJoueurDeux;
  private InterfaceJoueur interfaceJoueurTrois;
  private InterfaceJoueur interfaceJoueurZaku;
  private Joueur joueurEsku;
  private Joueur joueurDeux;
  private Joueur joueurTrois;
  private Joueur joueurZaku;
  private Opposants opposants;
  private Manche.Score score;
  private AffichageEvenementsDeJeu evenementsDeJeu;
  private Tour tour;
}
