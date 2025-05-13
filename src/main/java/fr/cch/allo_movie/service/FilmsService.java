package fr.cch.allo_movie.service;

import fr.cch.allo_movie.entity.Films;
import fr.cch.allo_movie.exceptions.CustomException;
import fr.cch.allo_movie.repository.FilmsRepository;
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
   * Le constructeur
   * @param filmRepository Injection du repository
   */
  public FilmsService(FilmsRepository filmRepository) {
    this.filmRepository = filmRepository;
  }

  /**
   * Méthode pour ajouter un film
   * @return le film ajouté
   */
  public Films save(Films film) {
    return filmRepository.save(film);
  }

  /**
   * Méthode pour trouver tous les films
   * @return la liste des films
   */
  public List<Films> findAll() {
    return filmRepository.findAll();
  }

  /**
   * Méthode pour trouver un film par son id (retourne l'entité complète)
   * @param id l'id du film recherché
   * @return le film trouvé
   */
  public Films findById(Long id) {
    return filmRepository.findById(id)
      .orElseThrow(() -> new CustomException("Films", "id", id)); // Renvoie l'entité
  }

  /**
   * Mettre à jour un film
   * @param film L'objet à mettre à jour
   * @return L'objet mis à jour
   */
  public Films updateFilm(Films film) {
    Optional<Films> isFilmExist= filmRepository.findById(film.getId());

    if (isFilmExist.isPresent()) {
      Films existingFilm = isFilmExist.get();

      existingFilm.setTitre(film.getTitre());
      existingFilm.setDateSortie(film.getDateSortie());
      existingFilm.setSynopsis(film.getSynopsis());
      existingFilm.setImage(film.getImage());
      existingFilm.setNoteMoyenne(film.getNoteMoyenne());
      return filmRepository.save(existingFilm);
    } else {
      throw new CustomException("L'film n'existe pas", "id", film.getId());
    }
  }

  /**
   * Méthode pour supprimer un film par son Id
   * @param id L'identifiant de l'film à supprimer
   * @return L'objet supprimé
   */
  public Films deleteById(Long id) {
    // Récupérer l'objet dans un Optional
    Optional<Films> optionalFilms = filmRepository.findById(id);

    // Vérifier si l'objet existe
    if (optionalFilms.isPresent()) {
      Films film = optionalFilms.get();
      filmRepository.delete(film); // Supprimer l'objet
      return film; // Retourner l'objet supprimé
    } else {
      throw new CustomException("Films", "id",  id);
    }
  }

  /**
   * Supprimer tous les films
   */
  public void deleteAll() {
    filmRepository.deleteAll();
  }

}
