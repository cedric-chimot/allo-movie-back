package fr.cch.allo_movie.dtos;

import fr.cch.allo_movie.entity.Films;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FilmUpdateDTO {

  private Films film;

  private List<Long> categories;

  private List<Long> realisateurs;

  private List<ActeurFilmDTO> acteurs;

}
