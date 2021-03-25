package com.montaury.mus.jeu;

import com.montaury.mus.jeu.joueur.AffichageEvenementsDeJeu;
import com.montaury.mus.jeu.joueur.InterfaceJoueur;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;
import com.montaury.mus.jeu.tour.phases.dialogue.Hordago;
import com.montaury.mus.jeu.tour.phases.dialogue.Kanta;
import com.montaury.mus.jeu.tour.phases.dialogue.Tira;
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
    interfaceJoueurDeux = mock(InterfaceJoueur.class);
    interfaceJoueurTrois = mock(InterfaceJoueur.class);
    interfaceJoueurZaku = mock(InterfaceJoueur.class);
    Joueur joueurEsku = new Joueur("J1", interfaceJoueurEsku);
    Joueur joueurDeux = new Joueur("J2", interfaceJoueurDeux);
    Joueur joueurTrois = new Joueur("J3", interfaceJoueurTrois);
    Joueur joueurZaku = new Joueur("J4", interfaceJoueurZaku);
    opposants = new Opposants(joueurEsku, joueurDeux, joueurTrois, joueurZaku);
    partie = new Partie(mock(AffichageEvenementsDeJeu.class));
  }

  @Test
  void devrait_faire_gagner_la_premiere_equipe_a_3_manches() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Hordago());
    when(interfaceJoueurDeux.faireChoixParmi(any())).thenReturn(new Kanta());
    when(interfaceJoueurTrois.faireChoixParmi(any())).thenReturn(new Tira());
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Tira());

    Partie.Resultat resultat = partie.jouer(opposants);

    assertThat(resultat.vainqueur()).isNotNull();
    assertThat(resultat.score().resultatManches()).hasSizeGreaterThanOrEqualTo(3);
  }

  private InterfaceJoueur interfaceJoueurEsku;
  private InterfaceJoueur interfaceJoueurDeux;
  private InterfaceJoueur interfaceJoueurTrois;
  private InterfaceJoueur interfaceJoueurZaku;
  private Opposants opposants;
  private Partie partie;
}