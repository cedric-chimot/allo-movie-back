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

  private final CategorieFilmsRepository categorieFilmsRepository;
  private final CategorieRepository categorieRepository;
  private final FilmsRepository filmRepository;

  public CategorieFilmsService(
    CategorieFilmsRepository categorieFilmsRepository,
    CategorieRepository categorieRepository,
    FilmsRepository filmRepository
  ) {
    this.categorieFilmsRepository = categorieFilmsRepository;
    this.categorieRepository = categorieRepository;
    this.filmRepository = filmRepository;
  }

  public List<CategorieFilms> findByFilmsId(Long filmId) {
    return categorieFilmsRepository.findByFilmsId(filmId);
  }

  /**
   * Ajouter une liaison entre une catégorie et un film
   * @param idCategorie l'id de la catégorie
   * @param idFilm l'id du film
   * @return la liaison créée
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
}
