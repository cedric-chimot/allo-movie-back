package fr.cch.allo_movie.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FilmCreateDTO {

  private String titre;
  private Long dateSortie;
  private String synopsis;
  private String image;
  private List<Long> categories;

}
