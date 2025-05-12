package fr.cch.allo_movie.controller;

import fr.cch.allo_movie.entity.Categorie;
import fr.cch.allo_movie.service.CategorieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin
public class CategorieController {

  /**
   * Le service des catégories
   */
  private final CategorieService categorieService;

  /**
   * Le constructeur
   * @param categorieService Injection du service
   */
  public CategorieController(CategorieService categorieService) {
    this.categorieService = categorieService;
  }

  /**
   * Ajouter une nouvelle catégorie
   * @param categorie la catégorie à ajouter
   * @return la catégorie nouvellement ajoutée
   */
  @PostMapping("/create")
  public ResponseEntity<Categorie> saveCategorie(@RequestBody Categorie categorie) {
    Categorie categorieSave = categorieService.save(categorie);
    return ResponseEntity.ok(categorieSave);
  }

  /**
   * Afficher la liste de toutes les catégories
   * @return la liste des catégories
   */
  @GetMapping("/all")
  public List<Categorie> findAllCategorie() {
    return categorieService.findAll();
  }

  /**
   * Trouver une catégorie par son id
   * @param id l'id d'une catégorie
   * @return la catégorie recherchée
   */
  @GetMapping("/{id}")
  public Categorie findById(@PathVariable Long id) {
    return categorieService.findById(id);
  }

  /**
   * Mettre à jour une catégorie
   * @param categorie la catégorie à mettre à jour
   * @return la catégorie mise à jour
   */
  @PutMapping("/update")
  public Categorie updateCategorie(@RequestBody Categorie categorie) {
    return categorieService.updateCategorie(categorie);
  }

  /**
   * Supprimer une catégorie par son id
   * @param id l'id de la catégorie à supprimer
   * @return la catégorie supprimée
   */
  @DeleteMapping("/delete/{id}")
  public Categorie deleteCategorieById(@PathVariable Long id) {
    return categorieService.deleteById(id);
  }

  /**
   * Supprimer toutes les catégories
   */
  @DeleteMapping("/delete/all")
  public void deleteAllCategorie() {
    categorieService.deleteAll();
  }

}
