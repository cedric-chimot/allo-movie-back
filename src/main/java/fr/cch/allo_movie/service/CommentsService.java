package fr.cch.allo_movie.service;

import fr.cch.allo_movie.entity.Films;
import fr.cch.allo_movie.entity.Users;
import fr.cch.allo_movie.entity.Comments;
import fr.cch.allo_movie.exceptions.CustomException;
import fr.cch.allo_movie.repository.CommentsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentsService {

  /**
   * Le repository des commentaires
   */
  private final CommentsRepository userRepository;

  /**
   * Le service des utilisateurs
   */
  private final UsersService usersService;

  /**
   * Le service des film
   */
  private final FilmsService filmsService;

  /**
   * Le constructeur
   * @param userRepository Injection du repository
   */
  public CommentsService(CommentsRepository userRepository, UsersService usersService, FilmsService filmsService) {
    this.userRepository = userRepository;
    this.usersService = usersService;
    this.filmsService = filmsService;
  }

  /**
   * Méthode pour ajouter un commentaire
   * @param comment le commentaire
   * @param dateComment la date d'ajout
   * @param note la note donnée au film
   * @param idUser l'utilisateur qui commente
   * @param idFilm le film commenté
   * @return
   */
  public Comments save(String comment, Long dateComment, Long note, Long idUser, Long idFilm) {
    Users user = usersService.findById(idUser);
    Films film = filmsService.findById(idFilm);

    Comments comments = new Comments(comment, dateComment, note, user, film);

    return userRepository.save(comments);
  }

  /**
   * Méthode pour trouver tous les commentaires
   * @return la liste des commentaires
   */
  public List<Comments> findAll() {
    return userRepository.findAll();
  }

  /**
   * Méthode pour trouver un commentaire par son id
   * @param id l'id du commentaire recherché
   * @return le commentaire trouvé
   */
  public Comments findById(Long id) {
    return userRepository.findById(id)
      .orElseThrow(() -> new CustomException("Comments", "id", id)); // Renvoie l'entité
  }

  /**
   * Mettre à jour un commentaire
   * @param user L'objet à mettre à jour
   * @return L'objet mis à jour
   */
  public Comments updateComment(Comments comment) {
    Optional<Comments> isCommentExist= userRepository.findById(comment.getId());

    if (isCommentExist.isPresent()) {
      Comments existingComment = isCommentExist.get();

      existingComment.setComment(comment.getComment());
      existingComment.setDateComment(comment.getDateComment());
      existingComment.setNote(comment.getNote());
      existingComment.setUser(comment.getUser());
      existingComment.setFilm(comment.getFilm());
      return userRepository.save(existingComment);
    } else {
      throw new CustomException("L'commentaire n'existe pas", "id", comment.getId());
    }
  }

  /**
   * Méthode pour supprimer un commentaire par son Id
   * @param id L'identifiant du commentaire à supprimer
   * @return L'objet supprimé
   */
  public Comments deleteById(Long id) {
    // Récupérer l'objet dans un Optional
    Optional<Comments> optionalComments = userRepository.findById(id);

    // Vérifier si l'objet existe
    if (optionalComments.isPresent()) {
      Comments user = optionalComments.get();
      userRepository.delete(user); // Supprimer l'objet
      return user; // Retourner l'objet supprimé
    } else {
      throw new CustomException("Comments", "id",  id);
    }
  }

  /**
   * Supprimer tous les commentaires
   */
  public void deleteAll() {
    userRepository.deleteAll();
  }

}
