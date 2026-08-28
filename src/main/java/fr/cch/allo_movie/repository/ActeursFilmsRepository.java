package fr.cch.allo_movie.repository;

import fr.cch.allo_movie.entity.ActeursFilms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActeursFilmsRepository extends JpaRepository<ActeursFilms, Long> {

  List<ActeursFilms> findByFilmsId(Long filmId);

}
