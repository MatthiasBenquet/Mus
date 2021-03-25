package com.montaury.mus.jeu.joueur;

import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.carte.ValeurCarte;
import com.montaury.mus.jeu.carte.paires.Doubles;
import com.montaury.mus.jeu.carte.paires.Meds;
import com.montaury.mus.jeu.carte.paires.Paires;
import com.montaury.mus.jeu.carte.paires.Simple;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

  public static final int TAILLE = 4;

  public static Main vide() {
    return new Main(new ArrayList<>());
  }

  private final List<Carte> cartes;

  Main(List<Carte> cartes) {
    this.cartes = cartes;
  }

  public List<Carte> cartes() {
    return cartes;
  }

  public List<Carte> cartesDuPlusGrandAuPlusPetit() {
    ArrayList<Carte> cartesTriees = new ArrayList<>(this.cartes);
    cartesTriees.sort((c1, c2) -> c2.comparerAvec(c1).valeurComparator());
    return cartesTriees;
  }

  public Paires getPaires(){
      List<Paires> paires = cartes.stream()
              .collect(Collectors.groupingBy(Carte::valeurCarte))
              .entrySet().stream().filter(groupe -> groupe.getValue().size() > 1)
              .map(groupe -> creerPaires(groupe.getKey(), groupe.getValue().size()))
              .collect(Collectors.toList());
      return paires.size() > 1 ? new Doubles((Simple) paires.get(0), (Simple) paires.get(1)) : paires.get(0);
  }

  private Paires creerPaires(ValeurCarte valeurCarte, int nombreCartesDeMemeValeur) {
    if (nombreCartesDeMemeValeur == 2) {
      return new Simple(valeurCarte);
    }
    if (nombreCartesDeMemeValeur == 3) {
      return new Meds(valeurCarte);
    }
    // carré
    return new Doubles(new Simple(valeurCarte), new Simple(valeurCarte));
  }

  public int pointsPourJeu() {
    return cartes.stream().mapToInt(Carte::pointsPourJeu).sum();
  }

  public void ajouter(List<Carte> nouvellesCartes) {
    cartes.addAll(nouvellesCartes);
  }

  public void retirer(List<Carte> cartesARetirer) {
    cartes.removeAll(cartesARetirer);
  }

  public boolean aDesPaires() {
    return cartes.stream().mapToInt(Carte::valeur).distinct().count() < TAILLE;
  }

  public void jeterTout() {
    cartes.clear();
  }

  public int nombreCartesManquantes() {
    return TAILLE - cartes.size();
  }
}
