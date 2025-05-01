package fr.cch.allo_movie.repository;

import fr.cch.allo_movie.entity.CategorieFilms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieFilmsRepository extends JpaRepository<CategorieFilms, Long> {
}
