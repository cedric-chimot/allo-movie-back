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
@Table(name = "reponses")
public class Reponses {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "date_reponse")
  private Long dateReponse;

  @Column(name = "message")
  private String message;

  @Column(name = "id_reponse_enfant")
  private Reponses reponseEnfant;

  @Column(name = "id_user")
  private Users users;

  @Column(name = "id_comment")
  private Comments comments;

  @JsonIgnore
  @OneToMany(mappedBy = "reponseSignal")
  private List<Signalements> signalementsList;

  public Reponses(Long dateReponse, String message, Reponses reponseEnfant, Users users, Comments comments) {
    this.dateReponse = dateReponse;
    this.message = message;
    this.reponseEnfant = reponseEnfant;
    this.users = users;
    this.comments = comments;
  }

  @Override
  public String toString() {
    return "Reponses{" +
      "id=" + id +
      ", dateReponse=" + dateReponse +
      ", message='" + message + '\'' +
      ", reponseEnfant=" + reponseEnfant +
      ", user=" + users +
      ", comment=" + comments +
      '}';
  }
}
