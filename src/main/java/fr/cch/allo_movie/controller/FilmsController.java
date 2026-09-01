package fr.cch.allo_movie.controller;

import fr.cch.allo_movie.dtos.FilmCreateDTO;
import fr.cch.allo_movie.dtos.FilmDetailDTO;
import fr.cch.allo_movie.entity.Films;
import fr.cch.allo_movie.service.FilmsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/films")
@CrossOrigin(origins = "http://localhost:4200")
public class FilmsController {

  /**
   * Le service des films
   */
  private final FilmsService filmService;

  /**
   * Le constructeur
   *
   * @param filmService Injection du service
   */
  public FilmsController(FilmsService filmService) {
    this.filmService = filmService;
  }

  /**
   * Afficher la liste de tous les films
   *
   * @return la liste des films
   */
  @GetMapping("/all")
  public List<Films> findAllFilms() {
    return filmService.findAll();
  }

  /**
   * Trouver un film par son id
   *
   * @param id l'id du film
   * @return le film recherché
   */
  @GetMapping("/{id}")
  public Films findById(@PathVariable Long id) {
    return filmService.findById(id);
  }

  /**
   * Afficher les 4 films les plus récemment sortis
   *
   * @return les 4 derniers films
   */
  @GetMapping("/latest")
  public List<Films> findLatestFilms() {
    return filmService.findLatestFilms();
  }

  /**
   * Récupérer les détails d'un film par son ID
   *
   * @param id L'id du film
   * @return Le film récupéré et ses infos détaillées
   */
  @GetMapping("/{id}/detail")
  public FilmDetailDTO findDetailById(@PathVariable Long id) {
    return filmService.findDetailById(id);
  }

  /**
   * Ajouter un nouveau film
   *
   * @param filmCreateDTO les données du film et les catégories
   * @return le film nouvellement ajouté
   */
  @PostMapping("/create")
  public ResponseEntity<Films> saveFilm(
    @RequestBody FilmCreateDTO filmCreateDTO
  ) {

    Films filmSave = filmService.save(filmCreateDTO);

    return ResponseEntity.ok(filmSave);
  }

  /**
   * Mettre à jour un film
   *
   * @param film le film à mettre à jour
   * @return le film mis à jour
   */
  @PutMapping("/update")
  public Films updateFilm(@RequestBody Films film) {
    return filmService.updateFilm(film);
  }

  /**
   * Supprimer un film par son id
   *
   * @param id l'id du film
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
