package fr.cch.allo_movie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "realisateurs")
public class Realisateurs {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "nom")
  private String nom;

  @Column(name = "prenom")
  private String prenom;

  public Realisateurs(String nom, String prenom) {
    this.nom = nom;
    this.prenom = prenom;
  }

  @Override
  public String toString() {
    return "Realisateurs{" +
      "id=" + id +
      ", nom='" + nom + '\'' +
      ", prenom='" + prenom + '\'' +
      '}';
  }
}
