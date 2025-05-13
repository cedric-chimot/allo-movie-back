package fr.cch.allo_movie.controller;

import fr.cch.allo_movie.entity.Films;
import fr.cch.allo_movie.service.FilmsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/films")
@CrossOrigin
public class FilmsController {

  /**
   * Le service des films
   */
  private final FilmsService filmService;

  /**
   * Le constructeur
   * @param filmService Injection du service
   */
  public FilmsController(FilmsService filmService) {
    this.filmService = filmService;
  }

  /**
   * Ajouter un nouveau film
   * @param film le film à ajouter
   * @return le film nouvellement ajouté
   */
  @PostMapping("/create")
  public ResponseEntity<Films> saveFilm(@RequestBody Films film) {
    Films filmSave = filmService.save(film);
    return ResponseEntity.ok(filmSave);
  }

  /**
   * Afficher la liste de tous les films
   * @return la liste des films
   */
  @GetMapping("/all")
  public List<Films> findAllFilms() {
    return filmService.findAll();
  }

  /**
   * Trouver un film par son id
   * @param id l'id d'un film
   * @return le film recherché
   */
  @GetMapping("/{id}")
  public Films findById(@PathVariable Long id) {
    return filmService.findById(id);
  }

  /**
   * Mettre à jour un film
   * @param film le film à mettre à jour
   * @return le film mis à jour
   */
  @PutMapping("/update")
  public Films updateFilm(@RequestBody Films film) {
    return filmService.updateFilm(film);
  }

  /**
   * Supprimer un film par son id
   * @param id l'id de l'film à supprimer
   * @return le film supprimé
   */
  @DeleteMapping("/delete/{id}")
  public Films deleteFilmById(@PathVariable Long id) {
    return filmService.deleteById(id);
  }

  /**
   * Supprimer tous les films
   */
  @DeleteMapping("/delete/all")
  public void deleteAllFilms() {
    filmService.deleteAll();
  }

}
