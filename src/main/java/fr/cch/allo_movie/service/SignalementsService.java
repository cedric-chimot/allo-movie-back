package fr.cch.allo_movie.service;

import fr.cch.allo_movie.entity.Signalements;
import fr.cch.allo_movie.entity.Films;
import fr.cch.allo_movie.entity.Users;
import fr.cch.allo_movie.exceptions.CustomException;
import fr.cch.allo_movie.repository.SignalementsRepository;

import java.util.List;
import java.util.Optional;

public class SignalementsService {

  /**
   * Le repository des signalements
   */
  private final SignalementsRepository signalementRepository;

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
   * @param signalementRepository Injection du repository
   */
  public SignalementsService(SignalementsRepository signalementRepository, UsersService usersService, FilmsService filmsService) {
    this.signalementRepository = signalementRepository;
    this.usersService = usersService;
    this.filmsService = filmsService;
  }

  /**
   * Méthode pour ajouter un signalement
   * @param signalement le signalement
   * @param dateComment la date d'ajout
   * @param note la note donnée au film
   * @param idUser l'utilisateur qui signalemente
   * @param idFilm le film signalementé
   * @return
   */
  public Signalements save(String signalement, Long dateComment, Long note, Long idUser, Long idFilm) {
    Users user = usersService.findById(idUser);
    Films film = filmsService.findById(idFilm);

    Signalements signalements = new Signalements(signalement, dateComment, note, user, film);

    return signalementRepository.save(signalements);
  }

  /**
   * Méthode pour trouver tous les signalements
   * @return la liste des signalements
   */
  public List<Signalements> findAll() {
    return signalementRepository.findAll();
  }

  /**
   * Méthode pour trouver un signalement par son id
   * @param id l'id du signalement recherché
   * @return le signalement trouvé
   */
  public Signalements findById(Long id) {
    return signalementRepository.findById(id)
      .orElseThrow(() -> new CustomException("Signalements", "id", id)); // Renvoie l'entité
  }

  /**
   * Mettre à jour un signalement
   * @param user L'objet à mettre à jour
   * @return L'objet mis à jour
   */
  public Signalements updateComment(Signalements signalement) {
    Optional<Signalements> isCommentExist= signalementRepository.findById(signalement.getId());

    if (isCommentExist.isPresent()) {
      Signalements existingComment = isCommentExist.get();

      existingComment.setComment(signalement.getComment());
      existingComment.setDateComment(signalement.getDateComment());
      existingComment.setNote(signalement.getNote());
      existingComment.setUser(signalement.getUser());
      existingComment.setFilm(signalement.getFilm());
      return signalementRepository.save(existingComment);
    } else {
      throw new CustomException("L'signalement n'existe pas", "id", signalement.getId());
    }
  }

  /**
   * Méthode pour supprimer un signalement par son Id
   * @param id L'identifiant du signalement à supprimer
   * @return L'objet supprimé
   */
  public Signalements deleteById(Long id) {
    // Récupérer l'objet dans un Optional
    Optional<Signalements> optionalSignalements = signalementRepository.findById(id);

    // Vérifier si l'objet existe
    if (optionalSignalements.isPresent()) {
      Signalements user = optionalSignalements.get();
      signalementRepository.delete(user); // Supprimer l'objet
      return user; // Retourner l'objet supprimé
    } else {
      throw new CustomException("Signalements", "id",  id);
    }
  }

  /**
   * Supprimer tous les signalements
   */
  public void deleteAll() {
    signalementRepository.deleteAll();
  }

}

}
