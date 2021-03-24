package com.montaury.mus.jeu.joueur;

import java.util.List;

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

    public List<Joueur> joueurs() { return List.of(this.joueur1, this.joueur2); }

    public String nom() { return this.nom; }
}
