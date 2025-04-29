package fr.cch.allo_movie.repository;

import fr.cch.allo_movie.entity.Films;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmsRepository extends JpaRepository<Films, Long> {
}
