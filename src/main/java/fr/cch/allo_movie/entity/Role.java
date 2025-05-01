package fr.cch.allo_movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "role")
  private String role;

  @JsonIgnore
  @OneToMany(mappedBy = "role")
  private List<Users> usersList;

  @Override
  public String toString() {
    return "Role{" +
      "id=" + id +
      ", role='" + role + '\'' +
      '}';
  }
}
