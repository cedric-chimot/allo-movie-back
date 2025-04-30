package fr.cch.allo_movie.repository;

import fr.cch.allo_movie.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
}
