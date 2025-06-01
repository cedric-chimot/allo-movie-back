package fr.cch.allo_movie.service;

import fr.cch.allo_movie.entity.Reponses;
import fr.cch.allo_movie.entity.Signalements;
import fr.cch.allo_movie.entity.Users;
import fr.cch.allo_movie.enums.MotifsEnum;
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
  private final ReponsesService reponsesService;

  /**
   * Le constructeur
   * @param signalementRepository Injection du repository
   */
  public SignalementsService(SignalementsRepository signalementRepository, UsersService usersService, ReponsesService reponsesService) {
    this.signalementRepository = signalementRepository;
    this.usersService = usersService;
    this.reponsesService = reponsesService;
  }

  /**
   * Méthode pour ajouter un signalement
   * @param motif le motif du signalement
   * @param dateSignalement la date
   * @param message le message d'accompagnement
   * @param idUser l'utilisateur qui signale
   * @param idReponse la réponse signalée
   * @return le nouveau signalement
   */
  public Signalements save(String motif, Long dateSignalement, String message, Long idUser, Long idReponse) {
    Users user = usersService.findById(idUser);
    Reponses reponse = reponsesService.findById(idReponse);

    // Convertir la chaîne de caractères en enum
    MotifsEnum motifEnum;
    try {
      motifEnum = MotifsEnum.valueOf(motif);
    } catch (IllegalArgumentException e) {
      throw new CustomException("Motif invalide : ", "motif", motif);
    }

    Signalements signalements = new Signalements(motifEnum, dateSignalement, message, user, reponse);

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
   * @param id l'id du signalement
   * @param motif le motif
   * @param dateSignalement la date
   * @param message le message d'accompagnement
   * @param idUser l'utilisateur qui signale
   * @param idReponse la réponse signalée
   * @return le signalement mis à jour
   */
  public Signalements updateSignalement(Long id, String motif, Long dateSignalement, String message, Long idUser, Long idReponse) {
    Optional<Signalements> optionalSignalement = signalementRepository.findById(id);

    if (optionalSignalement.isEmpty()) {
      throw new CustomException("Le signalement n'existe pas", "id", id);
    }

    Signalements existingSignalement = optionalSignalement.get();

    try {
      // Conversion du motif
      MotifsEnum motifEnum = MotifsEnum.valueOf(motif);
      existingSignalement.setMotif(motifEnum);
    } catch (IllegalArgumentException e) {
      throw new CustomException("Motif invalide", "motif", motif);
    }

    // Mise à jour des autres champs
    existingSignalement.setDateSignalement(dateSignalement);
    existingSignalement.setMessage(message);

    // Mise à jour des relations
    Users user = usersService.findById(idUser);
    existingSignalement.setUserSignal(user);

    Reponses reponse = reponsesService.findById(idReponse);
    existingSignalement.setReponseSignal(reponse);

    return signalementRepository.save(existingSignalement);
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
