package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;
import java.util.List;

public class Jeu extends Phase {

  private static final List<Integer> RANG_JEUX = List.of(31, 32, 40, 37, 36, 35, 34, 33);

  public Jeu() {
    super("Jeu");
  }

  public static boolean aLeJeu(Joueur joueur) {
    return RANG_JEUX.contains(joueur.main().pointsPourJeu());
  }

  @Override
  protected boolean peutParticiper(Joueur joueur) {
    return aLeJeu(joueur);
  }

  @Override
  protected Joueur meilleurParmi(Opposants opposants) {

    Joueur meilleurJoueur = opposants.joueurEsku();
    if(rangDuJeu(opposants.joueurEsku()) > rangDuJeu(opposants.joueurDeux())){
      meilleurJoueur = opposants.joueurDeux();
    }
    else if(rangDuJeu(opposants.joueurDeux()) > rangDuJeu(opposants.joueurTrois())){
      meilleurJoueur = opposants.joueurTrois();
    }
    else if(rangDuJeu(opposants.joueurTrois()) > rangDuJeu(opposants.joueurZaku())) {
      meilleurJoueur = opposants.joueurZaku();
    }
    return meilleurJoueur;
  }

  private int rangDuJeu(Joueur joueur) {
    return RANG_JEUX.indexOf(joueur.main().pointsPourJeu());
  }

  @Override
  public int pointsBonus(Joueur vainqueur) {
    return vainqueur.main().pointsPourJeu() == 31 ? 3 : 2;
  }
}
