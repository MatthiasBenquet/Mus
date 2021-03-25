package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.carte.ValeurCarte;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Main;
import com.montaury.mus.jeu.joueur.Opposants;

import java.util.ArrayList;
import java.util.List;

import static com.montaury.mus.jeu.carte.ValeurCarte.Comparaison.PLUS_GRANDE;
import static com.montaury.mus.jeu.carte.ValeurCarte.Comparaison.PLUS_PETITE;

public class Petit extends Phase {
  public Petit() {
    super("Petit");
  }

  @Override
  protected Joueur meilleurParmi(Opposants opposants) {
    Joueur joueurEsku = opposants.joueurEsku();
    Joueur joueurDeux = opposants.joueurDeux();
    Joueur joueurTrois = opposants.joueurTrois();
    Joueur joueurZaku = opposants.joueurZaku();
    List<Carte> cartesJoueurEsku = joueurEsku.main().cartesDuPlusGrandAuPlusPetit();
    List<Carte> cartesJoueurDeux = joueurDeux.main().cartesDuPlusGrandAuPlusPetit();
    List<Carte> cartesJoueurTrois = joueurTrois.main().cartesDuPlusGrandAuPlusPetit();
    List<Carte> cartesJoueurZaku = joueurZaku.main().cartesDuPlusGrandAuPlusPetit();
    List<Carte> meilleurMain = joueurEsku.main().cartesDuPlusGrandAuPlusPetit();

    List<List<Carte>> tabCartesDesJoueurs = new ArrayList<List<Carte>>() {
    };
    tabCartesDesJoueurs.add(cartesJoueurEsku);
    tabCartesDesJoueurs.add(cartesJoueurDeux);
    tabCartesDesJoueurs.add(cartesJoueurTrois);
    tabCartesDesJoueurs.add(cartesJoueurZaku);

    ValeurCarte.Comparaison compare;

    for (int i = 0; i < Main.TAILLE; i++) {
      for (int j = 0; j < 4; j++) {
        compare = meilleurMain.get(i).comparerAvec(tabCartesDesJoueurs.get(j).get(i));

        if (compare == PLUS_GRANDE) {
          meilleurMain = tabCartesDesJoueurs.get(j);
        }
      }

    }
    if (meilleurMain == cartesJoueurEsku) {
      return joueurEsku;
    } else if (meilleurMain == cartesJoueurDeux) {
      return joueurDeux;
    } else if (meilleurMain == cartesJoueurTrois) {
      return joueurTrois;
    } else if (meilleurMain == cartesJoueurZaku) {
      return joueurZaku;
    }

    return joueurEsku;

  }
}
