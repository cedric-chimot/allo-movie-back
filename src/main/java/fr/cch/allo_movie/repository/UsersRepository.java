package fr.cch.allo_movie.repository;

import fr.cch.allo_movie.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends JpaRepository<Users, Long> {

  /**
   * Requête pour compter le nombre total d'utilisateurs
   * @return le nombre total d'utilisateurs
   */
  @Query("SELECT COUNT(u) FROM Users u")
  Long countUsers();

  /**
   * Requête pour trouver un utilisateur par son email
   * @param email l'email de l'utilisateur
   * @return l'utilisateur trouvé
   */
  @Query("SELECT u FROM Users u WHERE u.email = :email")
  Users findByEmail(@Param("email") String email);

}
