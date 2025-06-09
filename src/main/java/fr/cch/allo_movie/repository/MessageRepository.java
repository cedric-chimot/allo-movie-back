package fr.cch.allo_movie.repository;

import fr.cch.allo_movie.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Messages, Long> {
}
