package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;

public class Paires extends Phase {

  public Paires() {
    super("Paires");
  }

  @Override
  protected boolean peutParticiper(Joueur joueur) {
    return joueur.main().aDesPaires();
  }

  @Override
  protected Joueur meilleurParmi(Opposants opposants) {
    boolean [] participant = {
            peutParticiper(opposants.joueurEsku()),
            peutParticiper(opposants.joueurDeux()),
            peutParticiper(opposants.joueurTrois()),
            peutParticiper(opposants.joueurZaku())
    };

    com.montaury.mus.jeu.carte.paires.Paires pairesJoueurEsku;
    com.montaury.mus.jeu.carte.paires.Paires pairesJoueurDeux;
    com.montaury.mus.jeu.carte.paires.Paires pairesJoueurTrois;
    com.montaury.mus.jeu.carte.paires.Paires pairesJoueurZaku;

    pairesJoueurEsku = (participant[0]) ? opposants.joueurEsku().main().getPaires() : null;
    pairesJoueurDeux = (participant[1]) ? opposants.joueurDeux().main().getPaires() : null;
    pairesJoueurTrois = (participant[2]) ? opposants.joueurTrois().main().getPaires() : null;
    pairesJoueurZaku = (participant[3]) ? opposants.joueurZaku().main().getPaires() : null;


    com.montaury.mus.jeu.carte.paires.Paires meilleuresPaires;

    if (pairesJoueurEsku != null) {
      meilleuresPaires = pairesJoueurEsku;
    }
    else if (pairesJoueurDeux != null) {
      meilleuresPaires = pairesJoueurDeux;
    }
    else if (pairesJoueurTrois != null) {
      meilleuresPaires = pairesJoueurTrois;
    }
    else {
      meilleuresPaires = pairesJoueurZaku;
    }

    if(meilleuresPaires == pairesJoueurEsku){
      if(!meilleuresPaires.estMeilleureOuEgaleA(pairesJoueurDeux)){
        meilleuresPaires = pairesJoueurDeux;
      }
      else if(!meilleuresPaires.estMeilleureOuEgaleA(pairesJoueurTrois)){
        meilleuresPaires = pairesJoueurTrois;
      }
      else if(!meilleuresPaires.estMeilleureOuEgaleA(pairesJoueurZaku)){
        meilleuresPaires = pairesJoueurZaku;
      }
    }
    if (meilleuresPaires == pairesJoueurDeux) {
      if (!meilleuresPaires.estMeilleureOuEgaleA(pairesJoueurTrois)) {
        meilleuresPaires = pairesJoueurTrois;
      } else if (!meilleuresPaires.estMeilleureOuEgaleA(pairesJoueurZaku)) {
        meilleuresPaires = pairesJoueurZaku;
      }
    }
    if (meilleuresPaires == pairesJoueurTrois){
      if (!meilleuresPaires.estMeilleureOuEgaleA(pairesJoueurZaku)){
        meilleuresPaires = pairesJoueurZaku;
      }
    }


    if (meilleuresPaires == pairesJoueurEsku) {
      return opposants.joueurEsku();
    }
    else if (meilleuresPaires == pairesJoueurDeux){
      return opposants.joueurDeux();
    }
    else if (meilleuresPaires == pairesJoueurTrois){
      return opposants.joueurTrois();
    }
    else {
      return opposants.joueurZaku();
    }
  }

  @Override
  public int pointsBonus(Joueur vainqueur) {
    return vainqueur.main().getPaires().pointsBonus();
  }
}
