package fr.cch.allo_movie.repository;

import fr.cch.allo_movie.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}
