package fr.cch.allo_movie.repository;

import fr.cch.allo_movie.entity.Favoris;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavorisRepository extends JpaRepository<Favoris, Long> {
}
