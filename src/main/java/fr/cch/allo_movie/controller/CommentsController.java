package fr.cch.allo_movie.controller;

import fr.cch.allo_movie.entity.Comments;
import fr.cch.allo_movie.service.CommentsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin
public class CommentsController {

  /**
   * Le service des commentaires
   */
  private final CommentsService commentService;

  /**
   * Le constructeur
   * @param commentService Injection du service
   */
  public CommentsController(CommentsService commentService) {
    this.commentService = commentService;
  }

  /**
   * Ajouter un nouvel commentaire
   * @param comment le commentaire à ajouter
   * @return le commentaire nouvellement ajouté
   */
  @PostMapping("/create")
  public ResponseEntity<Comments> saveComment(@RequestBody Comments comment) {
    Comments commentSave = commentService.save(
      comment.getComment(),
      comment.getDateComment(),
      comment.getNote(),
      comment.getUser().getId(),
      comment.getFilm().getId()
    );
    return ResponseEntity.ok(commentSave);
  }

  /**
   * Afficher la liste de tous les commentaires
   * @return la liste des commentaires
   */
  @GetMapping("/all")
  public List<Comments> getAllComments() {
    return commentService.findAll();
  }

  /**
   * Trouver un commentaire par son id
   * @param id l'id d'un commentaire
   * @return le commentaire recherché
   */
  @GetMapping("/{id}")
  public Comments getCommentById(@PathVariable Long id) {
    return commentService.findById(id);
  }

  /**
   * Mettre à jour un commentaire
   * @param comment le commentaire à mettre à jour
   * @return le commentaire mis à jour
   */
  @PutMapping("/update")
  public Comments updateComment(@RequestBody Comments comment) {
    return commentService.updateComment(comment);
  }

  /**
   * Supprimer un commentaire par son id
   * @param id l'id du commentaire à supprimer
   * @return le commentaire supprimé
   */
  @DeleteMapping("/delete/{id}")
  public Comments deleteCommentById(@PathVariable Long id) {
    return commentService.deleteById(id);
  }

  /**
   * Supprimer tous les commentaires
   */
  @DeleteMapping("/delete/all")
  public void deleteAllComments() {
    commentService.deleteAll();
  }

}
