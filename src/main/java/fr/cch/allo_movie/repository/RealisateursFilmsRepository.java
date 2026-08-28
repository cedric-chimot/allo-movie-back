package fr.cch.allo_movie.repository;

import fr.cch.allo_movie.entity.RealisateursFilms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RealisateursFilmsRepository extends JpaRepository<RealisateursFilms, Long> {

  List<RealisateursFilms> findByFilmsId(Long filmId);

}
