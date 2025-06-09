package fr.cch.allo_movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_user")
  private Long id;

  @Column(name = "pseudo")
  private String pseudo;

  @Column(name = "email")
  private String email;

  @Column(name = "mdp")
  private String mdp;

  @ManyToOne
  @JoinColumn(name = "id_role")
  private Role role;

  @Column(name = "avertissements")
  private Long avertissements;

  @Column(name = "date_ban")
  private Long dateBan;

  @Column(name = "est_banni")
  private Boolean estBanni;

  @Column(name = "date_inscrit", updatable = false)
  @CreationTimestamp
  private LocalDateTime dateInscrit;

  @JsonIgnore
  @OneToMany(mappedBy = "user")
  private List<Comments> commentsList;

  @JsonIgnore
  @OneToMany(mappedBy = "users")
  private List<Reponses> reponsesList;

  @JsonIgnore
  @OneToMany(mappedBy = "userSignal")
  private List<Signalements> signalementsList;

  @JsonIgnore
  @OneToMany(mappedBy = "userMessage")
  private List<Message> messageList;

  // Constructeur pour save() basique
  public Users(String pseudo, String email, String mdp, Role role, LocalDateTime dateInscrit) {
    this.pseudo = pseudo;
    this.email = email;
    this.mdp = mdp;
    this.role = role;
    this.dateInscrit = dateInscrit;
  }

  @Override
  public String toString() {
    return "Users{" +
      "id=" + id +
      ", pseudo='" + pseudo + '\'' +
      ", email='" + email + '\'' +
      ", mdp='" + mdp + '\'' +
      ", role=" + role +
      ", avertissements=" + avertissements +
      ", dateBan=" + dateBan +
      ", estBanni=" + estBanni +
      ", dateInscrit=" + dateInscrit +
      '}';
  }
}
