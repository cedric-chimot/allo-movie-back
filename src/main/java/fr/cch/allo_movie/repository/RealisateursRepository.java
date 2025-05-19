package fr.cch.allo_movie.repository;

import fr.cch.allo_movie.entity.Realisateurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RealisateursRepository extends JpaRepository<Realisateurs, Long> {

  /**
   * Requête pour compter le nombre total de réalisateurs
   * @return le nombre total de réalisateurs
   */
  @Query("SELECT COUNT(u) FROM Realisateurs u")
  Long countRealisateurs();

}
