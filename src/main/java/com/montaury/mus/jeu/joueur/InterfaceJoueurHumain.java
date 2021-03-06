package com.montaury.mus.jeu.joueur;

import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.tour.phases.dialogue.Choix;
import com.montaury.mus.jeu.tour.phases.dialogue.Gehiago;
import com.montaury.mus.jeu.tour.phases.dialogue.Hordago;
import com.montaury.mus.jeu.tour.phases.dialogue.Idoki;
import com.montaury.mus.jeu.tour.phases.dialogue.Imido;
import com.montaury.mus.jeu.tour.phases.dialogue.Kanta;
import com.montaury.mus.jeu.tour.phases.dialogue.Paso;
import com.montaury.mus.jeu.tour.phases.dialogue.Tira;
import com.montaury.mus.jeu.tour.phases.dialogue.TypeChoix;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InterfaceJoueurHumain implements InterfaceJoueur {

  private final Scanner scanner = new Scanner(System.in);
  private Main main;

  @Override
  public boolean veutAllerMus() {
    String choix;
    do {
      println("Souhaitez-vous aller mus ? (o/n)");
      choix = scanner.next();
    }
    while (!choix.equals("o") && !choix.equals("n"));
    return choix.equals("o");
  }

  @Override
  public List<Carte> cartesAJeter() {
    println("Veuillez saisir les cartes à jeter (ex: 1,3) :");
    String aJeter = scanner.next();
    return Arrays.stream(aJeter.split(","))
      .mapToInt(Integer::parseInt)
      .mapToObj(indiceCarte -> main.cartesDuPlusGrandAuPlusPetit().get(indiceCarte - 1))
      .collect(Collectors.toList());
  }

  @Override
  public Choix faireChoixParmi(List<TypeChoix> choixPossibles) {
    print("Faites un choix entre (en toutes lettres): ");


    //saisie vérif du choix
    for (;;){
      println(choixPossibles.stream().map(TypeChoix::nom).collect(Collectors.joining(" | ")));
      String choix = scanner.next();
      if (choix.equalsIgnoreCase("Paso")) return new Paso();
      if (choix.equalsIgnoreCase("Imido")) return new Imido();
      if (choix.equalsIgnoreCase("Hordago")) return new Hordago();
      if (choix.equalsIgnoreCase("Idoki")) return new Idoki();
      if (choix.equalsIgnoreCase("Tira")) return new Tira();
      if (choix.equalsIgnoreCase("Gehiago")) return new Gehiago(1);
      if (choix.equalsIgnoreCase("Kanta")) return new Kanta();
      print("Faites un choix valide s'il vous plaît : ");
    }
  }

  @Override
  public void nouvelleMain(Main main) {
    this.main = main;
  }

  private void println(String string) {
    System.out.println(string);
  }

  private void print(String string) {
    System.out.print(string);
  }
}
