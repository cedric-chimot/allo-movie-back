package fr.cch.allo_movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "films")
public class Films {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "titre")
  private String titre;

  @Column(name = "date_sortie")
  private Long dateSortie;

  @Column(name = "synopsis")
  private String synopsis;

  @Column(name = "image")
  private String image;

  @Column(name = "note_moyenne")
  private Long noteMoyenne;

  @JsonIgnore
  @OneToMany(mappedBy = "films", cascade = CascadeType.ALL)
  private List<CategorieFilms> categorieFilmsList;

  @JsonIgnore
  @ManyToMany
  @JoinTable(
    name = "categorie_films",
    joinColumns = @JoinColumn(name = "film_id"),
    inverseJoinColumns = @JoinColumn(name = "categorie_id")
  )
  private Set<Categorie> categories = new HashSet<>();

  @JsonIgnore
  @OneToMany(mappedBy = "film")
  private List<Comments> commentsList;

  public Films(String titre, Long dateSortie, String synopsis, String image, Long noteMoyenne) {
    this.titre = titre;
    this.dateSortie = dateSortie;
    this.synopsis = synopsis;
    this.image = image;
    this.noteMoyenne = noteMoyenne;
  }

  @Override
  public String toString() {
    return "Films{" +
      "id=" + id +
      ", titre='" + titre + '\'' +
      ", dateSortie=" + dateSortie +
      ", synopsis='" + synopsis + '\'' +
      ", image='" + image + '\'' +
      ", noteMoyenne=" + noteMoyenne +
      '}';
  }
}
