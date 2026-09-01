package fr.cch.allo_movie.dtos;

import fr.cch.allo_movie.entity.Films;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FilmCreateDTO {

  private Films film;

  private List<Long> categories;

}
