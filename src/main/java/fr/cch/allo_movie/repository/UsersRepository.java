package fr.cch.allo_movie.repository;

import fr.cch.allo_movie.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
