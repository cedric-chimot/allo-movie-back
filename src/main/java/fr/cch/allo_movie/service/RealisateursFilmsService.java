package fr.cch.allo_movie.service;

import fr.cch.allo_movie.entity.Films;
import fr.cch.allo_movie.entity.Realisateurs;
import fr.cch.allo_movie.entity.RealisateursFilms;
import fr.cch.allo_movie.exceptions.CustomException;
import fr.cch.allo_movie.repository.FilmsRepository;
import fr.cch.allo_movie.repository.RealisateursFilmsRepository;
import fr.cch.allo_movie.repository.RealisateursRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RealisateursFilmsService {

  /**
   * Importation des répositories
   */
  private final RealisateursFilmsRepository realisateursFilmsRepository;
  private final RealisateursRepository realisateursRepository;
  private final FilmsRepository filmRepository;

  /**
   * Le constructeur du service
   * @param realisateursFilmsRepository le répo de la relation entre réalisateurs et films
   * @param realisateursRepository le répo réalisateurs
   * @param filmRepository le répo des films
   */
  public RealisateursFilmsService(
    RealisateursFilmsRepository realisateursFilmsRepository,
    RealisateursRepository realisateursRepository,
    FilmsRepository filmRepository
  ) {
    this.realisateursFilmsRepository = realisateursFilmsRepository;
    this.realisateursRepository = realisateursRepository;
    this.filmRepository = filmRepository;
  }

  /**
   * Méthode pour trouver le(s) réalisateur(s) d'un film par son/ses id(s)
   * @param filmId l'id du film
   * @return La liste des réalisateurs d'un film
   */
  public List<RealisateursFilms> findByFilmsId(Long filmId) {
    return realisateursFilmsRepository.findByFilmsId(filmId);
  }

  /**
   * Méthode pour associer un acteur à un film
   * @param idRealisateur l'id du réalisateur
   * @param idFilm l'id du film
   * @return la nouvelle relation créée
   */
  public RealisateursFilms save(
    Long idRealisateur,
    Long idFilm
  ) {

    Realisateurs realisateur =
      realisateursRepository.findById(idRealisateur)
        .orElseThrow(() ->
          new CustomException(
            "Realisateurs",
            "id",
            idRealisateur
          )
        );

    Films film = filmRepository.findById(idFilm)
      .orElseThrow(() ->
        new CustomException("Films", "id", idFilm)
      );

    RealisateursFilms realisateursFilms =
      new RealisateursFilms(realisateur, film);

    return realisateursFilmsRepository.save(realisateursFilms);
  }

  /**
   * Méthode pour supprimer une association
   * @param realisateursFilms l'association à supprimer
   */
  public void delete(RealisateursFilms realisateursFilms) {
    realisateursFilmsRepository.delete(realisateursFilms);
  }

}
