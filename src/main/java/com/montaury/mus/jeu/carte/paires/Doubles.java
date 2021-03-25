package com.montaury.mus.jeu.carte.paires;

public class Doubles extends Paires {

  private final Simple meilleurePaire;
  private final Simple moinsBonnePaire;

  public Doubles(Simple paire1, Simple paire2) {
    super(3);
    this.meilleurePaire = paire1.valeur().valeur() > paire2.valeur().valeur() ? paire1 : paire2;
    this.moinsBonnePaire = paire1.valeur().valeur() > paire2.valeur().valeur() ? paire2 : paire1;
  }

  @Override
  public boolean estMeilleureOuEgaleA(Paires paires) {
    if (meilleurePaire == null || moinsBonnePaire == null) {return false;}
    if (!(paires instanceof Doubles)) {
      return true;
    }
    Doubles autresDoubles = (Doubles) paires;
    if (meilleurePaire.valeur().valeur() > autresDoubles.meilleurePaire.valeur().valeur()) {
      return true;
    }
    if (meilleurePaire.valeur() == autresDoubles.meilleurePaire.valeur()) {
      return moinsBonnePaire.valeur().valeur() >= autresDoubles.moinsBonnePaire.valeur().valeur();
    }
    return false;
  }
}
