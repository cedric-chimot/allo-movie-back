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
@Table(name = "realisateurs_films")
public class RealisateursFilms {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "categorie_id")
  private Realisateurs realisateurs;

  @ManyToOne
  @JoinColumn(name = "film_id")
  private Films films;

  public RealisateursFilms(Realisateurs realisateurs, Films films) {
    this.realisateurs = realisateurs;
    this.films = films;
  }

  @Override
  public String toString() {
    return "RealisateursFilms{" +
      "id=" + id +
      ", realisateurs=" + realisateurs +
      ", films=" + films +
      '}';
  }
}
