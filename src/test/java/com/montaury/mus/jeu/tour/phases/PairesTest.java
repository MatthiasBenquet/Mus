package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;
import org.junit.jupiter.api.Test;

import static com.montaury.mus.jeu.joueur.Fixtures.main;
import static com.montaury.mus.jeu.joueur.Fixtures.unJoueurAvec;
import static org.assertj.core.api.Assertions.assertThat;

class PairesTest {
  @Test
  void ne_doit_pas_se_derouler_si_personne_n_a_de_paires() {
    Opposants opposants = new Opposants(
      unJoueurAvec(main(Carte.AS_BATON, Carte.QUATRE_PIECE, Carte.VALET_BATON, Carte.SIX_COUPE)),
      unJoueurAvec(main(Carte.AS_PIECE, Carte.QUATRE_BATON, Carte.VALET_COUPE, Carte.SIX_EPEE)),
      unJoueurAvec(main(Carte.AS_EPEE, Carte.QUATRE_EPEE, Carte.VALET_EPEE, Carte.SIX_BATON)),
      unJoueurAvec(main(Carte.VALET_PIECE, Carte.SIX_PIECE, Carte.QUATRE_COUPE, Carte.AS_PIECE))
    );

    boolean peutSeDerouler = new Paires().peutSeDerouler(opposants);

    assertThat(peutSeDerouler).isFalse();
  }

  @Test
  void ne_doit_pas_se_derouler_si_un_des_joueurs_n_a_pas_de_paires() {
    Opposants opposants = new Opposants(
      unJoueurAvec(main(Carte.AS_BATON, Carte.QUATRE_PIECE, Carte.VALET_BATON, Carte.SIX_COUPE)),
      unJoueurAvec(main(Carte.VALET_PIECE, Carte.SIX_PIECE, Carte.SIX_EPEE, Carte.AS_PIECE)),
      unJoueurAvec(main(Carte.VALET_PIECE, Carte.SIX_PIECE, Carte.SIX_EPEE, Carte.AS_PIECE)),
      unJoueurAvec(main(Carte.VALET_PIECE, Carte.SIX_PIECE, Carte.SIX_EPEE, Carte.AS_PIECE))
    );

    boolean peutSeDerouler = new Paires().peutSeDerouler(opposants);

    assertThat(peutSeDerouler).isFalse();
  }

  @Test
  void devrait_se_derouler_si_les_deux_joueurs_ont_des_paires() {
    Opposants opposants = new Opposants(
      unJoueurAvec(main(Carte.AS_BATON, Carte.QUATRE_PIECE, Carte.VALET_BATON, Carte.QUATRE_COUPE)),
      unJoueurAvec(main(Carte.AS_BATON, Carte.QUATRE_PIECE, Carte.VALET_BATON, Carte.QUATRE_COUPE)),
      unJoueurAvec(main(Carte.AS_BATON, Carte.QUATRE_PIECE, Carte.VALET_BATON, Carte.QUATRE_COUPE)),
      unJoueurAvec(main(Carte.VALET_PIECE, Carte.SIX_PIECE, Carte.SIX_EPEE, Carte.AS_PIECE))
    );

    boolean peutSeDerouler = new Paires().peutSeDerouler(opposants);

    assertThat(peutSeDerouler).isTrue();
  }

  @Test
  void devrait_faire_gagner_le_joueur_ayant_la_meilleure_paire() {
    Joueur joueurZaku = unJoueurAvec(main(Carte.VALET_PIECE, Carte.SIX_PIECE, Carte.SIX_EPEE, Carte.AS_PIECE));
    Opposants opposants = new Opposants(
      unJoueurAvec(main(Carte.AS_BATON, Carte.QUATRE_PIECE, Carte.VALET_BATON, Carte.QUATRE_COUPE)),
      unJoueurAvec(main(Carte.AS_BATON, Carte.QUATRE_PIECE, Carte.VALET_BATON, Carte.QUATRE_COUPE)),
      unJoueurAvec(main(Carte.AS_BATON, Carte.QUATRE_PIECE, Carte.VALET_BATON, Carte.QUATRE_COUPE)),
      joueurZaku
    );

    Joueur vainqueur = new Paires().meilleurParmi(opposants);

    assertThat(vainqueur).isEqualTo(joueurZaku);
  }

  @Test
  void devrait_donner_un_bonus_de_1_si_le_joueur_a_une_paire_simple() {
    Joueur joueur = unJoueurAvec(main(Carte.VALET_PIECE, Carte.SIX_PIECE, Carte.SIX_EPEE, Carte.AS_PIECE));

    int bonus = new Paires().pointsBonus(joueur);

    assertThat(bonus).isEqualTo(1);
  }

  @Test
  void devrait_donner_un_bonus_de_2_si_le_joueur_a_des_meds() {
    Joueur joueur = unJoueurAvec(main(Carte.VALET_PIECE, Carte.SIX_PIECE, Carte.SIX_EPEE, Carte.SIX_COUPE));

    int bonus = new Paires().pointsBonus(joueur);

    assertThat(bonus).isEqualTo(2);
  }

  @Test
  void devrait_donner_un_bonus_de_3_si_le_joueur_a_des_doubles() {
    Joueur joueur = unJoueurAvec(main(Carte.VALET_PIECE, Carte.VALET_BATON, Carte.VALET_COUPE, Carte.VALET_EPEE));

    int bonus = new Paires().pointsBonus(joueur);

    assertThat(bonus).isEqualTo(3);
  }

}