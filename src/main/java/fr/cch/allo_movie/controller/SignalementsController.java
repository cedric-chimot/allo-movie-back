package fr.cch.allo_movie.controller;

import fr.cch.allo_movie.entity.Signalements;
import fr.cch.allo_movie.service.SignalementsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/signalements")
@CrossOrigin
public class SignalementsController {

  /**
   * Le service des signalements
   */
  private final SignalementsService signalementsService;

  /**
   * Le constructeur
   * @param signalementsService injection du service
   */
  public SignalementsController(SignalementsService signalementsService) {
    this.signalementsService = signalementsService;
  }

  /**
   * Créer un nouveau signalement
   * @param signalements le signalement à ajouter
   * @return le signalement nouvellement ajouté
   */
  @PostMapping("/create")
  public ResponseEntity<Signalements> saveSignalement(@RequestBody Signalements signalements) {
    Signalements signalementSave = signalementsService.save(
      String.valueOf(signalements.getMotif()),
      signalements.getDateSignalement(),
      signalements.getMessage(),
      signalements.getUserSignal().getId(),
      signalements.getReponseSignal().getId()
    );
    return ResponseEntity.ok(signalementSave);
  }

  /**
   * Récupérer tous les signalements
   * @return la liste de tous les signalements
   */
  @GetMapping("/all")
  public ResponseEntity<List<Signalements>> getAllSignalements() {
    return ResponseEntity.ok(signalementsService.findAll());
  }

  /**
   * Récupérer un signalement par son ID
   * @param id l'id du signalement
   * @return le signalement recherché
   */
  @GetMapping("/{id}")
  public ResponseEntity<Signalements> getSignalementById(@PathVariable Long id) {
    return ResponseEntity.ok(signalementsService.findById(id));
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
  @PatchMapping("/update")
  public ResponseEntity<Signalements> updateSignalement(
    @PathVariable Long id,
    @RequestParam String motif,
    @RequestParam Long dateSignalement,
    @RequestParam String message,
    @RequestParam Long idUser,
    @RequestParam Long idReponse
  ) {
    Signalements updated = signalementsService.updateSignalement(id, motif, dateSignalement, message, idUser, idReponse);
    return ResponseEntity.ok(updated);
  }

  /**
   * Supprimer un signalement par son ID
   * @param id l'id du signalement
   * @return le signalement supprimé
   */
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Signalements> deleteSignalement(@PathVariable Long id) {
    Signalements deleted = signalementsService.deleteById(id);
    return ResponseEntity.ok(deleted);
  }

  /**
   * Supprimer tous les signalements
   */
  @DeleteMapping("/delete/all")
  public void deleteAllSignalements() {
    signalementsService.deleteAll();
  }

}
