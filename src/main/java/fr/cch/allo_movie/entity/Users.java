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
@Table(name = "users")
public class Users {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
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

  @JsonIgnore
  @OneToMany(mappedBy = "user")
  private List<Comments> commentsList;

  @JsonIgnore
  @OneToMany(mappedBy = "users")
  private List<Reponses> reponsesList;

  @JsonIgnore
  @OneToMany(mappedBy = "userSignal")
  private List<Signalements> signalementsList;

  public Users(String pseudo, String email, String mdp, Role role, Long avertissements, Long dateBan, Boolean estBanni) {
    this.pseudo = pseudo;
    this.email = email;
    this.mdp = mdp;
    this.role = role;
    this.avertissements = avertissements;
    this.dateBan = dateBan;
    this.estBanni = estBanni;
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
      '}';
  }
}
