package com.montaury.mus.jeu.joueur;

public class Equipe {

    private Joueur joueurA ;
    private Joueur joueurB;
    private String nom ;


    public Equipe(Joueur joueurA,Joueur joueurB, String nom) {
        this.joueurA = joueurA;
        this.joueurB = joueurB;
        this.nom = nom ;

        joueurA.setEquipe(this) ;
        joueurB.setEquipe(this);

    }

    public Joueur joueurA() {
        return joueurA;
    }

    public Joueur joueurB() {
        return joueurB;
    }

    public String nom(){return nom ;}




}
