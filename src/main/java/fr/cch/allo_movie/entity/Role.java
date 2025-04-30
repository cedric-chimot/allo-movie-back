package fr.cch.allo_movie.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "role")
  private String role;

  @Override
  public String toString() {
    return "Role{" +
      "id=" + id +
      ", role='" + role + '\'' +
      '}';
  }
}
