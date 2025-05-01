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
@Table(name = "categorie_films")
public class CategorieFilms {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "id_categorie")
  private Categorie categorie;

  @ManyToOne
  @JoinColumn(name = "id_film")
  private Films films;

  public CategorieFilms(Categorie categorie, Films films) {
    this.categorie = categorie;
    this.films = films;
  }

  @Override
  public String toString() {
    return "CategorieFilms{" +
      "id=" + id +
      ", categorie=" + categorie +
      ", films=" + films +
      '}';
  }
}
