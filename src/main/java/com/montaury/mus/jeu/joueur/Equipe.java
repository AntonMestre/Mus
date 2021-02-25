package com.montaury.mus.jeu.joueur;

public class Equipe {
    private Joueur joueur1;
    private Joueur joueur2;
    private String nom;

    public Equipe (Joueur joueur1, Joueur joueur2, String nom) {
        this.joueur1 = joueur1;
        joueur1.setEquipe(this);

        this.joueur2 = joueur2;
        joueur2.setEquipe(this);

        this.nom = nom;
    }

    public Joueur getCoequipier(Joueur joueur) {
        return joueur == this.joueur1 ? joueur2 : joueur1;
    }
    public String nom() { return this.nom; }
}
