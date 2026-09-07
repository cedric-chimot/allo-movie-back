package fr.cch.allo_movie.service;

import fr.cch.allo_movie.entity.Acteurs;
import fr.cch.allo_movie.entity.ActeursFilms;
import fr.cch.allo_movie.entity.Films;
import fr.cch.allo_movie.exceptions.CustomException;
import fr.cch.allo_movie.repository.ActeursFilmsRepository;
import fr.cch.allo_movie.repository.ActeursRepository;
import fr.cch.allo_movie.repository.FilmsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ActeursFilmsService {

  /**
   * Importation des répositories
   */
  private final ActeursFilmsRepository acteursFilmsRepository;
  private final ActeursRepository acteursRepository;
  private final FilmsRepository filmRepository;

  /**
   * Le constructeur du service
   * @param acteursFilmsRepository le répo de la relation entre réalisateurs et films
   * @param acteursRepository le répo réalisateurs
   * @param filmRepository le répo des films
   */
  public ActeursFilmsService(
    ActeursFilmsRepository acteursFilmsRepository,
    ActeursRepository acteursRepository,
    FilmsRepository filmRepository
  ) {
    this.acteursFilmsRepository = acteursFilmsRepository;
    this.acteursRepository = acteursRepository;
    this.filmRepository = filmRepository;
  }

  /**
   * Méthode pour trouver les acteurs d'un film par leurs ids
   * @param filmId l'id du film
   * @return La liste des acteurs d'un film
   */
  public List<ActeursFilms> findByFilmsId(Long filmId) {
    return acteursFilmsRepository.findByFilmsId(filmId);
  }

  /**
   * Méthode pour associer un acteur à un film
   * @param idActeur l'id de l'acteur
   * @param idFilm l'id du film
   * @return la nouvelle relation créée
   */
  public ActeursFilms save(
    Long idActeur,
    Long idFilm,
    String role
  ) {

    Acteurs acteur = acteursRepository.findById(idActeur)
      .orElseThrow(() ->
        new CustomException(
          "Acteurs",
          "id",
          idActeur
        )
      );

    Films film = filmRepository.findById(idFilm)
      .orElseThrow(() ->
        new CustomException("Films", "id", idFilm)
      );

    ActeursFilms acteursFilms =
      new ActeursFilms(acteur, film, role);

    return acteursFilmsRepository.save(acteursFilms);
  }

  /**
   * Méthode pour mettre à jour le rôle d'un acteur
   *
   * @param acteursFilms la relation entre l'acteur et le film
   * @param role le nouveau rôle
   */
  public void updateRole(
    ActeursFilms acteursFilms,
    String role
  ) {
    acteursFilms.setRole(role);

    acteursFilmsRepository.save(acteursFilms);
  }

  /**
   * Méthode pour supprimer une association
   * @param acteursFilms l'association à supprimer
   */
  public void delete(ActeursFilms acteursFilms) {
    acteursFilmsRepository.delete(acteursFilms);
  }

}
