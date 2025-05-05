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
@Table(name = "acteurs_films")
public class ActeursFilms {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "categorie_id")
  private Acteurs acteurs;

  @ManyToOne
  @JoinColumn(name = "film_id")
  private Films films;

  @Column(name = "role")
  private String role;

  public ActeursFilms(Acteurs acteurs, Films films, String role) {
    this.acteurs = acteurs;
    this.films = films;
    this.role = role;
  }

  @Override
  public String toString() {
    return "ActeursFilms{" +
      "id=" + id +
      ", acteurs=" + acteurs +
      ", films=" + films +
      ", role='" + role + '\'' +
      '}';
  }
}
