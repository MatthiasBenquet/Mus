package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;
import org.junit.jupiter.api.Test;

import static com.montaury.mus.jeu.joueur.Fixtures.main;
import static com.montaury.mus.jeu.joueur.Fixtures.unJoueurAvec;
import static org.assertj.core.api.Assertions.assertThat;

class GrandTest {
  @Test
  void devrait_faire_gagner_le_joueur_esku_s_il_a_la_plus_grande_carte() {
    Joueur joueurEsku = unJoueurAvec(main(Carte.AS_COUPE, Carte.CINQ_COUPE, Carte.CAVALIER_COUPE, Carte.SIX_COUPE));
    Joueur joueurDeux = unJoueurAvec(main(Carte.DEUX_BATON, Carte.TROIS_BATON, Carte.SEPT_BATON, Carte.AS_BATON));
    Joueur joueurTrois = unJoueurAvec(main(Carte.DEUX_PIECE, Carte.TROIS_PIECE, Carte.SEPT_PIECE, Carte.AS_PIECE));
    Joueur joueurZaku = unJoueurAvec(main(Carte.DEUX_EPEE, Carte.TROIS_EPEE, Carte.SEPT_EPEE, Carte.AS_EPEE));

    Joueur vainqueur = new Grand().meilleurParmi(new Opposants(joueurEsku, joueurDeux, joueurTrois, joueurZaku));

    assertThat(vainqueur).isEqualTo(joueurEsku);
  }
  @Test
  void devrait_faire_gagner_le_joueur_zaku_s_il_a_la_plus_grande_carte() {
    Joueur joueurEsku = unJoueurAvec(main(Carte.AS_COUPE, Carte.CINQ_COUPE, Carte.SEPT_COUPE, Carte.SIX_COUPE));
    Joueur joueurDeux = unJoueurAvec(main(Carte.AS_BATON, Carte.CINQ_BATON, Carte.SEPT_BATON, Carte.SIX_BATON));
    Joueur joueurTrois = unJoueurAvec(main(Carte.AS_PIECE, Carte.CINQ_PIECE, Carte.SEPT_PIECE, Carte.SIX_PIECE));
    Joueur joueurZaku = unJoueurAvec(main(Carte.DEUX_EPEE, Carte.TROIS_EPEE, Carte.CAVALIER_EPEE, Carte.AS_EPEE));

    Joueur vainqueur = new Grand().meilleurParmi(new Opposants(joueurEsku, joueurDeux, joueurTrois, joueurZaku));

    assertThat(vainqueur).isEqualTo(joueurZaku);
  }

  @Test
  void devrait_faire_gagner_le_joueur_qui_a_la_seconde_plus_grande_carte_si_la_premiere_est_egale() {
    Joueur joueurEsku = unJoueurAvec(main(Carte.AS_COUPE, Carte.CINQ_COUPE, Carte.VALET_COUPE, Carte.SIX_COUPE));
    Joueur joueurDeux = unJoueurAvec(main(Carte.AS_BATON, Carte.CINQ_BATON, Carte.QUATRE_BATON, Carte.SIX_BATON));
    Joueur joueurTrois = unJoueurAvec(main(Carte.AS_PIECE, Carte.CINQ_PIECE, Carte.SEPT_PIECE, Carte.SIX_PIECE));
    Joueur joueurZaku = unJoueurAvec(main(Carte.DEUX_EPEE, Carte.VALET_EPEE, Carte.QUATRE_EPEE, Carte.SEPT_EPEE));

    Joueur vainqueur = new Grand().meilleurParmi(new Opposants(joueurEsku, joueurDeux, joueurTrois, joueurZaku));

    assertThat(vainqueur).isEqualTo(joueurZaku);
  }

  @Test
  void devrait_faire_gagner_le_joueur_esku_si_les_deux_mains_sont_egales() {
    Joueur joueurEsku = unJoueurAvec(main(Carte.AS_COUPE, Carte.QUATRE_COUPE, Carte.VALET_COUPE, Carte.SIX_COUPE));
    Joueur joueurDeux = unJoueurAvec(main(Carte.AS_BATON, Carte.QUATRE_BATON, Carte.SEPT_BATON, Carte.SIX_BATON));
    Joueur joueurTrois = unJoueurAvec(main(Carte.SEPT_PIECE, Carte.SIX_PIECE, Carte.QUATRE_PIECE, Carte.AS_PIECE));
    Joueur joueurZaku = unJoueurAvec(main(Carte.VALET_EPEE, Carte.SIX_EPEE, Carte.QUATRE_EPEE, Carte.AS_EPEE));

    Joueur vainqueur = new Grand().meilleurParmi(new Opposants(joueurEsku, joueurDeux, joueurTrois, joueurZaku));

    assertThat(vainqueur).isEqualTo(joueurEsku);
  }
}