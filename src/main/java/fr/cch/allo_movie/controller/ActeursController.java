package fr.cch.allo_movie.controller;

import fr.cch.allo_movie.entity.Acteurs;
import fr.cch.allo_movie.service.ActeursService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/acteurs")
@CrossOrigin
public class ActeursController {

  /**
   * Le service des acteurs
   */
  private final ActeursService acteurService;

  /**
   * Le constructeur
   * @param acteurService Injection du service
   */
  public ActeursController(ActeursService acteurService) {
    this.acteurService = acteurService;
  }

  /**
   * Ajouter un nouvel acteur
   * @param acteur l'acteur à ajouter
   * @return l'acteur nouvellement ajouté
   */
  @PostMapping("/create")
  public ResponseEntity<Acteurs> saveActeur(@RequestBody Acteurs acteur) {
    Acteurs acteurSave = acteurService.save(acteur);
    return ResponseEntity.ok(acteurSave);
  }

  /**
   * Afficher la liste de tous les acteurs
   * @return la liste des acteurs
   */
  @GetMapping("/all")
  public List<Acteurs> findAllActeurs() {
    return acteurService.findAll();
  }

  /**
   * Trouver un acteur par son id
   * @param id l'id d'un acteur
   * @return l'acteur recherché
   */
  @GetMapping("/{id}")
  public Acteurs findById(@PathVariable Long id) {
    return acteurService.findById(id);
  }

  /**
   * Mettre à jour un acteur
   * @param acteur l'acteur à mettre à jour
   * @return l'acteur mis à jour
   */
  @PutMapping("/update")
  public Acteurs updateActeur(@RequestBody Acteurs acteur) {
    return acteurService.updateActeur(acteur);
  }

  /**
   * Supprimer un acteur par son id
   * @param id l'id de l'acteur à supprimer
   * @return l'acteur supprimé
   */
  @DeleteMapping("/delete/{id}")
  public Acteurs deleteActeurById(@PathVariable Long id) {
    return acteurService.deleteById(id);
  }

  /**
   * Supprimer tous les acteurs
   */
  @DeleteMapping("/delete/all")
  public void deleteAllActeurs() {
    acteurService.deleteAll();
  }

}
