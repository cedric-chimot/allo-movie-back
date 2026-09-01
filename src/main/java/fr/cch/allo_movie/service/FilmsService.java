package fr.cch.allo_movie.service;

import fr.cch.allo_movie.dtos.ActeurDetailDTO;
import fr.cch.allo_movie.dtos.FilmDetailDTO;
import fr.cch.allo_movie.dtos.FilmCreateDTO;
import fr.cch.allo_movie.entity.ActeursFilms;
import fr.cch.allo_movie.entity.Films;
import fr.cch.allo_movie.entity.RealisateursFilms;
import fr.cch.allo_movie.exceptions.CustomException;
import fr.cch.allo_movie.repository.ActeursFilmsRepository;
import fr.cch.allo_movie.repository.FilmsRepository;
import fr.cch.allo_movie.repository.RealisateursFilmsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FilmsService {

  /**
   * Le repository des films
   */
  private final FilmsRepository filmRepository;

  /**
   * Les repositories pour les relations
   */
  private final ActeursFilmsRepository acteursFilmsRepository;
  private final RealisateursFilmsRepository realisateursFilmsRepository;

  /**
   * Le service pour gérer les relations film / catégorie
   */
  private final CategorieFilmsService categorieFilmsService;

  /**
   * Le constructeur
   */
  public FilmsService(FilmsRepository filmRepository, ActeursFilmsRepository acteursFilmsRepository, RealisateursFilmsRepository realisateursFilmsRepository,
    CategorieFilmsService categorieFilmsService
  ) {
    this.filmRepository = filmRepository;
    this.acteursFilmsRepository = acteursFilmsRepository;
    this.realisateursFilmsRepository = realisateursFilmsRepository;
    this.categorieFilmsService = categorieFilmsService;
  }

  /**
   * Méthode pour trouver tous les films
   *
   * @return la liste des films
   */
  public List<Films> findAll() {
    return filmRepository.findAll();
  }

  /**
   * Méthode pour trouver un film par son id
   *
   * @param id l'id du film recherché
   * @return le film trouvé
   */
  public Films findById(Long id) {
    return filmRepository.findById(id)
      .orElseThrow(() ->
        new CustomException("Films", "id", id)
      );
  }

  /**
   * Récupérer les 4 films les plus récemment sortis
   *
   * @return les 4 films avec les dates de sortie les plus récentes
   */
  public List<Films> findLatestFilms() {
    return filmRepository.findTop4ByOrderByDateSortieDesc();
  }

  /**
   * Récupérer un film et toutes ses informations détaillées
   *
   * @param id L'id du film
   * @return Le détail complet d'un film
   */
  public FilmDetailDTO findDetailById(Long id) {

    Films film = filmRepository.findById(id)
      .orElseThrow(() ->
        new CustomException("Films", "id", id)
      );

    List<RealisateursFilms> realisateursFilms =
      realisateursFilmsRepository.findByFilmsId(id);

    List<ActeursFilms> acteursFilms =
      acteursFilmsRepository.findByFilmsId(id);

    List<String> realisateurs = realisateursFilms.stream()
      .map(rf ->
        rf.getRealisateurs().getPrenom() + " " +
          rf.getRealisateurs().getNom()
      )
      .toList();

    List<ActeurDetailDTO> acteurs = acteursFilms.stream()
      .map(af -> new ActeurDetailDTO(
        af.getActeurs().getNom(),
        af.getActeurs().getPrenom(),
        af.getRole()
      ))
      .toList();

    List<fr.cch.allo_movie.entity.CategorieFilms> categorieFilms =
      categorieFilmsService.findByFilmsId(id);

    List<String> categories = categorieFilms.stream()
      .map(cf -> cf.getCategorie().getCategorie())
      .toList();

    return new FilmDetailDTO(
      film,
      realisateurs,
      acteurs,
      categories
    );
  }

  /**
   * Méthode pour ajouter un film
   *
   * @param filmDTO les données du film et les catégories sélectionnées
   * @return Le film ajouté
   */
  public Films save(FilmCreateDTO filmDTO) {

    // Récupérer le film complet envoyé par le formulaire
    Films film = filmDTO.getFilm();

    // Sauvegarder le film
    Films filmSauvegarde = filmRepository.save(film);

    // Créer une relation pour chaque catégorie
    if (filmDTO.getCategories() != null) {

      for (Long categorieId : filmDTO.getCategories()) {

        categorieFilmsService.save(
          categorieId,
          filmSauvegarde.getId()
        );
      }
    }

    return filmSauvegarde;
  }

  /**
   * Mettre à jour un film
   *
   * @param film L'objet à mettre à jour
   * @return L'objet mis à jour
   */
  public Films updateFilm(Films film) {

    Optional<Films> isFilmExist =
      filmRepository.findById(film.getId());

    if (isFilmExist.isPresent()) {

      Films existingFilm = isFilmExist.get();

      existingFilm.setTitre(film.getTitre());
      existingFilm.setDateSortie(film.getDateSortie());
      existingFilm.setSynopsis(film.getSynopsis());
      existingFilm.setResumeLong(film.getResumeLong());
      existingFilm.setDuree(film.getDuree());
      existingFilm.setImage(film.getImage());
      existingFilm.setNoteMoyenne(film.getNoteMoyenne());

      return filmRepository.save(existingFilm);

    } else {

      throw new CustomException(
        "Le film n'existe pas",
        "id",
        film.getId()
      );
    }
  }

  /**
   * Méthode pour supprimer un film par son Id
   *
   * @param id L'identifiant du film à supprimer
   * @return L'objet supprimé
   */
  public Films deleteById(Long id) {

    Optional<Films> optionalFilms =
      filmRepository.findById(id);

    if (optionalFilms.isPresent()) {

      Films film = optionalFilms.get();

      filmRepository.delete(film);

      return film;

    } else {

      throw new CustomException(
        "Films",
        "id",
        id
      );
    }
  }

  /**
   * Supprimer tous les films
   */
  public void deleteAll() {
    filmRepository.deleteAll();
  }
}
