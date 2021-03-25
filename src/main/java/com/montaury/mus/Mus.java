package com.montaury.mus;

import com.montaury.mus.jeu.Partie;
import com.montaury.mus.jeu.joueur.AffichageConsoleEvenementsDeJeu;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;
import java.util.Scanner;

public class Mus {
  public static void main(String[] args) {

    System.out.print("Entrez votre nom: ");

    String nomJoueur = new Scanner(System.in).next();

    Joueur humain = Joueur.humain(nomJoueur);
    Partie partie = new Partie(new AffichageConsoleEvenementsDeJeu(humain));

    Partie.Resultat resultat = partie.jouer(new Opposants(humain, Joueur.ordinateur("Méchant") , Joueur.ordinateur("Bot Albert"), Joueur.ordinateur("Pas Gentil")));

    System.out.println("Le vainqueur de la partie est " + resultat.vainqueur().nom());
  }
}
