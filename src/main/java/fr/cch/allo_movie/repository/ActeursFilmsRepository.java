package fr.cch.allo_movie.repository;

import fr.cch.allo_movie.entity.ActeursFilms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActeursFilmsRepository extends JpaRepository<ActeursFilms, Long> {
}
