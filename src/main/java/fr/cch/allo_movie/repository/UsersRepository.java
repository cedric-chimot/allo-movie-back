package fr.cch.allo_movie.repository;

import fr.cch.allo_movie.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsersRepository extends JpaRepository<Users, Long> {

  /**
   * Requête pour compter le nombre total d'utilisateurs
   * @return le nombre total d'utilisateurs
   */
  @Query("SELECT COUNT(u) FROM Users u")
  Long countUsers();

}
