package fr.cch.allo_movie.dtos;

import fr.cch.allo_movie.entity.Films;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FilmDetailDTO {

  private Films film;
  private String realisateur;
  private List<ActeurDetailDTO> acteurs;
  private List<String> categories;

}
