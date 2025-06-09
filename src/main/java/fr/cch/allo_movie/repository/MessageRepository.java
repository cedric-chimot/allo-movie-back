package fr.cch.allo_movie.repository;

import fr.cch.allo_movie.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
