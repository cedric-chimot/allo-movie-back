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
@Table(name = "categorie")
public class Categorie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "categorie")
  private String categorie;

  @Override
  public String toString() {
    return "Categorie{" +
      "id=" + id +
      ", categorie='" + categorie + '\'' +
      '}';
  }
}
