package fr.cch.allo_movie.service;

import fr.cch.allo_movie.dtos.*;
import fr.cch.allo_movie.entity.ActeursFilms;
import fr.cch.allo_movie.entity.CategorieFilms;
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
   * Le service pour gérer les relations entre les films et les acteurs, catégories et réalisateurs
   */
  private final CategorieFilmsService categorieFilmsService;
  private final ActeursFilmsService acteursFilmsService;
  private final RealisateursFilmsService realisateursFilmsService;

  /**
   * Le constructeur
   */
  public FilmsService(FilmsRepository filmRepository, ActeursFilmsRepository acteursFilmsRepository, RealisateursFilmsRepository realisateursFilmsRepository,
                      CategorieFilmsService categorieFilmsService, ActeursFilmsService acteursFilmsService, RealisateursFilmsService realisateursFilmsService
  ) {
    this.filmRepository = filmRepository;
    this.acteursFilmsRepository = acteursFilmsRepository;
    this.realisateursFilmsRepository = realisateursFilmsRepository;
    this.categorieFilmsService = categorieFilmsService;
    this.acteursFilmsService = acteursFilmsService;
    this.realisateursFilmsService = realisateursFilmsService;
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
        af.getActeurs().getId(),
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
   * @param filmDTO L'objet à mettre à jour
   * @return L'objet mis à jour
   */
  public Films updateFilm(FilmUpdateDTO filmDTO) {

    Films film = filmDTO.getFilm();

    Optional<Films> isFilmExist =
      filmRepository.findById(film.getId());

    if (isFilmExist.isPresent()) {

      Films existingFilm = isFilmExist.get();

      // Mise à jour des informations du film
      existingFilm.setTitre(film.getTitre());
      existingFilm.setDateSortie(film.getDateSortie());
      existingFilm.setSynopsis(film.getSynopsis());
      existingFilm.setResumeLong(film.getResumeLong());
      existingFilm.setDuree(film.getDuree());
      existingFilm.setImage(film.getImage());
      existingFilm.setNoteMoyenne(film.getNoteMoyenne());

      // --------------------------------
      // Catégories
      // --------------------------------

      List<CategorieFilms> categoriesExistantes =
        categorieFilmsService.findByFilmsId(film.getId());

      // Vérifier les relations existantes
      for (CategorieFilms relation : categoriesExistantes) {

        Long categorieId = relation.getCategorie().getId();

        if (filmDTO.getCategories() == null ||
          !filmDTO.getCategories().contains(categorieId)) {

          categorieFilmsService.delete(relation);
        }
      }

      // Vérifier les nouvelles catégories
      if (filmDTO.getCategories() != null) {

        for (Long categorieId : filmDTO.getCategories()) {

          boolean relationExiste = categoriesExistantes.stream()
            .anyMatch(relation ->
              relation.getCategorie().getId().equals(categorieId)
            );

          if (!relationExiste) {

            // La relation n'existe pas
            // donc on la crée
            categorieFilmsService.save(
              categorieId,
              film.getId()
            );
          }
        }
      }

      // --------------------------------
      // Réalisateurs
      // --------------------------------

      List<RealisateursFilms> realisateursExistants =
        realisateursFilmsService.findByFilmsId(film.getId());

      // Vérifier les relations existantes
      for (RealisateursFilms relation : realisateursExistants) {

        Long realisateurId =
          relation.getRealisateurs().getId();

        if (filmDTO.getRealisateurs() == null ||
          !filmDTO.getRealisateurs().contains(realisateurId)) {

          realisateursFilmsService.delete(relation);
        }
      }

      // Vérifier les nouveaux réalisateurs
      if (filmDTO.getRealisateurs() != null) {

        for (Long realisateurId : filmDTO.getRealisateurs()) {

          boolean relationExiste = realisateursExistants.stream()
            .anyMatch(relation ->
              relation.getRealisateurs().getId().equals(realisateurId)
            );

          if (!relationExiste) {

            // La relation n'existe pas
            // donc on la crée
            realisateursFilmsService.save(
              realisateurId,
              film.getId()
            );
          }
        }
      }

      // --------------------------------
      // Acteurs
      // --------------------------------

      List<ActeursFilms> acteursExistants =
        acteursFilmsService.findByFilmsId(film.getId());

      // Vérifier les relations existantes
      for (ActeursFilms relation : acteursExistants) {

        Long acteurId =
          relation.getActeurs().getId();

        ActeurFilmDTO nouvelActeur = null;

        if (filmDTO.getActeurs() != null) {

          nouvelActeur = filmDTO.getActeurs()
            .stream()
            .filter(acteur ->
              acteur.getActeurId().equals(acteurId)
            )
            .findFirst()
            .orElse(null);
        }

        if (nouvelActeur != null) {

          // La relation existe toujours
          // donc on met à jour le rôle
          acteursFilmsService.updateRole(
            relation,
            nouvelActeur.getRole()
          );

        } else {

          // L'acteur n'est plus sélectionné
          // donc on supprime la relation
          acteursFilmsService.delete(relation);
        }
      }

      // Vérifier les nouveaux acteurs
      if (filmDTO.getActeurs() != null) {

        for (ActeurFilmDTO nouvelActeur : filmDTO.getActeurs()) {

          boolean relationExiste = acteursExistants.stream()
            .anyMatch(relation ->
              relation.getActeurs().getId()
                .equals(nouvelActeur.getActeurId())
            );

          if (!relationExiste) {

            // La relation n'existe pas
            // donc on la crée
            acteursFilmsService.save(
              nouvelActeur.getActeurId(),
              film.getId(),
              nouvelActeur.getRole()
            );
          }
        }
      }

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
