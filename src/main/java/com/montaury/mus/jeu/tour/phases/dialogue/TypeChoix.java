package com.montaury.mus.jeu.tour.phases.dialogue;

import java.util.List;

import static java.util.Collections.emptyList;

public enum TypeChoix {
  MUS("Mus"),
  MINTZA("Mintza"),
  PASO("Paso"),
  IMIDO("Imido"),
  GEHIAGO("Gehiago"),
  HORDAGO("Hordago"),
  KANTA("Kanta"),
  IDOKI("Idoki"),
  TIRA("Tira");

  public static final List<TypeChoix> INITIAUX = List.of(PASO, IMIDO, HORDAGO);

  static {
    MUS.setChoixSuivantsPossibles(emptyList());
    MINTZA.setChoixSuivantsPossibles(emptyList());
    PASO.setChoixSuivantsPossibles(INITIAUX);
    IMIDO.setChoixSuivantsPossibles(List.of(GEHIAGO, HORDAGO, IDOKI, TIRA));
    GEHIAGO.setChoixSuivantsPossibles(List.of(GEHIAGO, HORDAGO, IDOKI, TIRA));
    HORDAGO.setChoixSuivantsPossibles(List.of(KANTA, TIRA));
    KANTA.setChoixSuivantsPossibles(emptyList());
    IDOKI.setChoixSuivantsPossibles(List.of(GEHIAGO, HORDAGO, IDOKI, TIRA));
    TIRA.setChoixSuivantsPossibles(List.of(GEHIAGO, HORDAGO, IDOKI, TIRA));
  }

  private final String nom;

  private List<TypeChoix> choixSuivantsPossibles;

  TypeChoix(String nom) {
    this.nom = nom;
  }

  public String nom() {
    return nom;
  }

  private void setChoixSuivantsPossibles(List<TypeChoix> choixSuivantsPossibles) {
    this.choixSuivantsPossibles = choixSuivantsPossibles;
  }

  public List<TypeChoix> choixSuivantsPossibles() {
    return choixSuivantsPossibles;
  }
}
