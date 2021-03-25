package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;

import static com.montaury.mus.jeu.tour.phases.Jeu.aLeJeu;

public class FauxJeu extends Phase {
  public FauxJeu() {
    super("Faux Jeu");
  }

  @Override
  protected boolean peutParticiper(Joueur joueur) {
    return !aLeJeu(joueur);
  }

  @Override
  protected Joueur meilleurParmi(Opposants opposants) {
    int pointsJoueurEsku = opposants.joueurEsku().main().pointsPourJeu();
    int pointsJoueurDeux = opposants.joueurDeux().main().pointsPourJeu();
    int pointsJoueurTrois = opposants.joueurTrois().main().pointsPourJeu();
    int pointsJoueurZaku = opposants.joueurZaku().main().pointsPourJeu();

    Joueur meilleurJoueur = opposants.joueurEsku();

    if (pointsJoueurEsku >= pointsJoueurDeux && pointsJoueurEsku >= pointsJoueurTrois && pointsJoueurEsku >= pointsJoueurZaku) {
      meilleurJoueur = opposants.joueurEsku();
    }
    else if(pointsJoueurDeux >= pointsJoueurTrois && pointsJoueurDeux >= pointsJoueurZaku) {
      meilleurJoueur = opposants.joueurDeux();
    }
    else if(pointsJoueurTrois >= pointsJoueurZaku){
      meilleurJoueur = opposants.joueurTrois();
    }
    else{
      meilleurJoueur = opposants.joueurZaku();
    }
    return meilleurJoueur;
  }

  @Override
  public int pointsBonus(Joueur vainqueur) {
    return 1;
  }
}
