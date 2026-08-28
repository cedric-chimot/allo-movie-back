package fr.cch.allo_movie.repository;

import fr.cch.allo_movie.entity.CategorieFilms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategorieFilmsRepository extends JpaRepository<CategorieFilms, Long> {

  List<CategorieFilms> findByFilmsId(Long filmId);

}
