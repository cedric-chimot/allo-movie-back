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
@Table(name = "signalements")
public class Signalements {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "motif")
  private String motif;

  @Column(name = "date_signalement")
  private Long dateSignalement;

  @Column(name = "message")
  private String message;

  @ManyToOne
  @JoinColumn(name = "id_user")
  private Users userSignal;

  @ManyToOne
  @JoinColumn(name = "id_reponse")
  private Reponses reponseSignal;

  public Signalements(String motif, Long dateSignalement, String message, Users userSignal, Reponses reponseSignal) {
    this.motif = motif;
    this.dateSignalement = dateSignalement;
    this.message = message;
    this.userSignal = userSignal;
    this.reponseSignal = reponseSignal;
  }

  @Override
  public String toString() {
    return "Signalements{" +
      "id=" + id +
      ", motif='" + motif + '\'' +
      ", dateSignalement=" + dateSignalement +
      ", message='" + message + '\'' +
      ", userSignal=" + userSignal +
      ", reponseSignal=" + reponseSignal +
      '}';
  }
}
