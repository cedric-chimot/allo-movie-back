package fr.cch.allo_movie.repository;

import fr.cch.allo_movie.entity.Acteurs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActeursRepository extends JpaRepository<Acteurs, Long> {
}
