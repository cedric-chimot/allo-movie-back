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
@Table(name = "favoris")
public class Favoris {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "film_id")
  private Films films;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private Users users;

  public Favoris(Films films, Users users) {
    this.films = films;
    this.users = users;
  }

  @Override
  public String toString() {
    return "Favoris{" +
      "id=" + id +
      ", films=" + films +
      ", users=" + users +
      '}';
  }
}
