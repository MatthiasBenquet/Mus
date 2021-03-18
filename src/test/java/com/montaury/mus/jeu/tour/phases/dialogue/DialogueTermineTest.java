package com.montaury.mus.jeu.tour.phases.dialogue;

import com.montaury.mus.jeu.joueur.Joueur;
import java.util.List;
import org.junit.jupiter.api.Test;

import static com.montaury.mus.jeu.joueur.Fixtures.unJoueur;
import static org.assertj.core.api.Assertions.assertThat;

class DialogueTermineTest {

  private final Joueur joueur1 = unJoueur();
  private final Joueur joueur2 = unJoueur();
  private final Joueur joueur3 = unJoueur();
  private final Joueur joueur4 = unJoueur();

  @Test
  void est_suspendu_si_le_dernier_choix_est_paso() {
    DialogueTermine dialogue = new DialogueTermine(List.of(
      new Dialogue.ChoixJoueur(new Paso(), joueur1),
      new Dialogue.ChoixJoueur(new Paso(), joueur2),
      new Dialogue.ChoixJoueur(new Paso(), joueur3),
      new Dialogue.ChoixJoueur(new Paso(), joueur4))
    );

    assertThat(dialogue.estSuspendu()).isTrue();
  }

  @Test
  void est_suspendu_si_le_dernier_choix_est_idoki() {
    DialogueTermine dialogue = new DialogueTermine(List.of(
      new Dialogue.ChoixJoueur(new Imido(), joueur1),
      new Dialogue.ChoixJoueur(new Imido(), joueur2),
      new Dialogue.ChoixJoueur(new Imido(), joueur3),
      new Dialogue.ChoixJoueur(new Idoki(), joueur4))
    );

    assertThat(dialogue.estSuspendu()).isTrue();
  }

  @Test
  void devrait_compter_1_point_engage_si_tous_les_joueurs_sont_paso() {
    DialogueTermine dialogue = new DialogueTermine(List.of(
      new Dialogue.ChoixJoueur(new Paso(), joueur1),
      new Dialogue.ChoixJoueur(new Paso(), joueur2),
      new Dialogue.ChoixJoueur(new Paso(), joueur3),
      new Dialogue.ChoixJoueur(new Paso(), joueur4))
    );

    assertThat(dialogue.pointsEngages()).isEqualTo(1);
  }

  @Test
  void devrait_compter_1_point_engage_pour_imido_tira() {
    DialogueTermine dialogue = new DialogueTermine(List.of(
      new Dialogue.ChoixJoueur(new Imido(), joueur1),
      new Dialogue.ChoixJoueur(new Tira(), joueur2),
      new Dialogue.ChoixJoueur(new Tira(), joueur3),
      new Dialogue.ChoixJoueur(new Tira(), joueur4))
    );

    assertThat(dialogue.pointsEngages()).isEqualTo(1);
  }

  @Test
  void devrait_compter_1_point_engage_pour_paso_imido_tira() {
    DialogueTermine dialogue = new DialogueTermine(List.of(
      new Dialogue.ChoixJoueur(new Paso(), joueur1),
      new Dialogue.ChoixJoueur(new Imido(), joueur2),
      new Dialogue.ChoixJoueur(new Tira(), joueur3),
      new Dialogue.ChoixJoueur(new Tira(), joueur4),
      new Dialogue.ChoixJoueur(new Tira(), joueur1))
    );

    assertThat(dialogue.pointsEngages()).isEqualTo(1);
  }

  @Test
  void devrait_compter_1_point_engage_pour_3_imido_tira() {
    DialogueTermine dialogue = new DialogueTermine(List.of(
      new Dialogue.ChoixJoueur(new Imido(3), joueur1)//,
      //new Dialogue.ChoixJoueur(new Tira(), joueur2),
      //new Dialogue.ChoixJoueur(new Tira(), joueur3),
      //new Dialogue.ChoixJoueur(new Tira(), joueur4)
            )
    );

    assertThat(dialogue.pointsEngages()).isEqualTo(1);
  }

  @Test
  void devrait_compter_1_point_engage_pour_hordago_tira() {
    DialogueTermine dialogue = new DialogueTermine(List.of(
      new Dialogue.ChoixJoueur(new Hordago(), joueur1),
      new Dialogue.ChoixJoueur(new Tira(), joueur2),
      new Dialogue.ChoixJoueur(new Tira(), joueur3),
      new Dialogue.ChoixJoueur(new Tira(), joueur4))
    );

    assertThat(dialogue.pointsEngages()).isEqualTo(1);
  }

  @Test
  void devrait_compter_2_points_engages_pour_imido_idoki() {
    DialogueTermine dialogue = new DialogueTermine(List.of(
      new Dialogue.ChoixJoueur(new Imido(), joueur1),
      new Dialogue.ChoixJoueur(new Idoki(), joueur2),
      new Dialogue.ChoixJoueur(new Tira(), joueur3),
      new Dialogue.ChoixJoueur(new Tira(), joueur4))
    );

    assertThat(dialogue.pointsEngages()).isEqualTo(2);
  }

  @Test
  void devrait_compter_2_points_engages_pour_imido_hordago_tira() {
    DialogueTermine dialogue = new DialogueTermine(List.of(
      new Dialogue.ChoixJoueur(new Imido(), joueur1),
      new Dialogue.ChoixJoueur(new Hordago(), joueur2),
      new Dialogue.ChoixJoueur(new Tira(), joueur3),
      new Dialogue.ChoixJoueur(new Tira(), joueur4),
      new Dialogue.ChoixJoueur(new Tira(), joueur1))
    );

    assertThat(dialogue.pointsEngages()).isEqualTo(2);
  }

}