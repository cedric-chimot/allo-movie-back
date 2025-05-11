package fr.cch.allo_movie.entity;

import fr.cch.allo_movie.enums.MotifsEnum;
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

  @Enumerated(EnumType.STRING)
  @Column(name = "motif")
  private MotifsEnum motif;

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

  public Signalements(MotifsEnum motif, Long dateSignalement, String message, Users userSignal, Reponses reponseSignal) {
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
