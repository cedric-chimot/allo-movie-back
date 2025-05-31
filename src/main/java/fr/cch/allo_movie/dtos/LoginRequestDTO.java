package fr.cch.allo_movie.dtos;

import lombok.Data;

@Data
public class LoginRequestDTO {
  private String email;
  private String mdp;

}
