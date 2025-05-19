package fr.cch.allo_movie.controller;

import fr.cch.allo_movie.entity.Realisateurs;
import fr.cch.allo_movie.service.RealisateursService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/realisateurs")
@CrossOrigin
public class RealisateursController {

  /**
   * Le service des réalisateurs
   */
  private final RealisateursService realisateurService;

  /**
   * Le constructeur
   * @param realisateurService Injection du service
   */
  public RealisateursController(RealisateursService realisateurService) {
    this.realisateurService = realisateurService;
  }

  /**
   * Ajouter un nouveau réalisateur
   * @param realisateur le réalisateur à ajouter
   * @return le réalisateur nouvellement ajouté
   */
  @PostMapping("/create")
  public Realisateurs saveRealisateur(@RequestBody Realisateurs realisateur) {
    return realisateurService.save(realisateur);
  }

  /**
   * Afficher la liste de tous les réalisateurs
   * @return la liste des réalisateurs
   */
  @GetMapping("/all")
  public List<Realisateurs> findAllRealisateurs() {
    return realisateurService.findAll();
  }

  /**
   * Trouver un réalisateur par son id
   * @param id l'id d'un réalisateur
   * @return le réalisateur recherché
   */
  @GetMapping("/{id}")
  public Realisateurs findById(@PathVariable Long id) {
    return realisateurService.findById(id);
  }

  /**
   * Compter le nombre de réalisateurs
   * @return le nombre de réalisateurs
   */
  @GetMapping("/count")
  public Long getCountRealisateurs() {
    return realisateurService.findRealisateursCount();
  }

  /**
   * Mettre à jour un réalisateur
   * @param realisateur le réalisateur à mettre à jour
   * @return le réalisateur mis à jour
   */
  @PutMapping("/update")
  public Realisateurs updateRealisateur(@RequestBody Realisateurs realisateur) {
    return realisateurService.updateRealisateur(realisateur);
  }

  /**
   * Supprimer un réalisateur par son id
   * @param id l'id du réalisateur à supprimer
   * @return le réalisateur supprimé
   */
  @DeleteMapping("/delete/{id}")
  public Realisateurs deleteRealisateurById(@PathVariable Long id) {
    return realisateurService.deleteById(id);
  }

  /**
   * Supprimer tous les réalisateurs
   */
  @DeleteMapping("/delete/all")
  public void deleteAllRealisateurs() {
    realisateurService.deleteAll();
  }

}
