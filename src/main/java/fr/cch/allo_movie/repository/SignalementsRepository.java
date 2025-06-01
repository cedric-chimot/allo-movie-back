package fr.cch.allo_movie.repository;

import fr.cch.allo_movie.entity.Signalements;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignalementsRepository extends JpaRepository<Signalements, Long> {
}
