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

  // Relation avec une autre réponse (réponse à une réponse précédente)
  @ManyToOne
  @JoinColumn(name = "id_reponse_enfant")
  private Reponses reponseEnfant;

  // Relation avec un utilisateur
  @ManyToOne
  @JoinColumn(name = "id_user")
  private Users users;

  // Relation avec un commentaire
  @ManyToOne
  @JoinColumn(name = "id_comment")
  private Comments comments;

  // Liste des réponses enfants, cette relation est mappée dans l'autre sens
  @JsonIgnore
  @OneToMany(mappedBy = "reponseEnfant")
  private List<Reponses> reponsesEnfants;

  // Liste des signalements associés à cette réponse
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
