package fr.cch.allo_movie.service;

import fr.cch.allo_movie.entity.Categorie;
import fr.cch.allo_movie.entity.CategorieFilms;
import fr.cch.allo_movie.entity.Films;
import fr.cch.allo_movie.exceptions.CustomException;
import fr.cch.allo_movie.repository.CategorieFilmsRepository;
import fr.cch.allo_movie.repository.CategorieRepository;
import fr.cch.allo_movie.repository.FilmsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CategorieFilmsService {

  /**
   * Importation des répositories
   */
  private final CategorieFilmsRepository categorieFilmsRepository;
  private final CategorieRepository categorieRepository;
  private final FilmsRepository filmRepository;

  /**
   * Le constructeur du service
   * @param categorieFilmsRepository le répo de la relation entre catégories et films
   * @param categorieRepository le répo catégories
   * @param filmRepository le répo des films
   */
  public CategorieFilmsService(
    CategorieFilmsRepository categorieFilmsRepository,
    CategorieRepository categorieRepository,
    FilmsRepository filmRepository
  ) {
    this.categorieFilmsRepository = categorieFilmsRepository;
    this.categorieRepository = categorieRepository;
    this.filmRepository = filmRepository;
  }

  /**
   * Méthode pour trouver une catégorie de film par son id
   * @param filmId l'id du film
   * @return La liste des catégories d'un film
   */
  public List<CategorieFilms> findByFilmsId(Long filmId) {
    return categorieFilmsRepository.findByFilmsId(filmId);
  }

  /**
   * Méthode pour associer une catégorie à un film
   * @param idCategorie l'id de la catégorie
   * @param idFilm l'id du film
   * @return la nouvelle relation créée
   */
  public CategorieFilms save(Long idCategorie, Long idFilm) {

    Categorie categorie = categorieRepository.findById(idCategorie)
      .orElseThrow(() ->
        new CustomException("Categorie", "id", idCategorie)
      );

    Films film = filmRepository.findById(idFilm)
      .orElseThrow(() ->
        new CustomException("Films", "id", idFilm)
      );

    CategorieFilms categorieFilms =
      new CategorieFilms(categorie, film);

    return categorieFilmsRepository.save(categorieFilms);
  }

  /**
   * Méthode pour supprimer une association
   * @param categorieFilms l'association à supprimer
   */
  public void delete(CategorieFilms categorieFilms) {
    categorieFilmsRepository.delete(categorieFilms);
  }
  
}
