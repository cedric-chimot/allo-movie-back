package fr.cch.allo_movie.repository;

import fr.cch.allo_movie.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
