package fr.cch.allo_movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comments {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "comment")
  private String comment;

  @Column(name = "date_comment")
  private Long dateComment;

  @Column(name = "note")
  private Long note;

  @ManyToOne
  @JoinColumn(name = "id_user")
  private Users user;

  @ManyToOne
  @JoinColumn(name = "id_film")
  private Films film;

  @JsonIgnore
  @OneToMany(mappedBy = "comments")
  private List<Reponses> reponsesList;

  public Comments(String comment, Long dateComment, Long note, Users user, Films film) {
    this.comment = comment;
    this.dateComment = dateComment;
    this.note = note;
    this.user = user;
    this.film = film;
  }

  @Override
  public String toString() {
    return "Comments{" +
      "id=" + id +
      ", comment='" + comment + '\'' +
      ", dateComment=" + dateComment +
      ", note=" + note +
      ", user=" + user +
      ", film=" + film +
      '}';
  }
}
