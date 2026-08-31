package fr.cch.allo_movie.service;

import fr.cch.allo_movie.entity.Films;
import fr.cch.allo_movie.exceptions.CustomException;
import fr.cch.allo_movie.repository.FilmsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import fr.cch.allo_movie.dtos.ActeurDetailDTO;
import fr.cch.allo_movie.dtos.FilmDetailDTO;
import fr.cch.allo_movie.entity.ActeursFilms;
import fr.cch.allo_movie.entity.CategorieFilms;
import fr.cch.allo_movie.entity.RealisateursFilms;
import fr.cch.allo_movie.repository.ActeursFilmsRepository;
import fr.cch.allo_movie.repository.CategorieFilmsRepository;
import fr.cch.allo_movie.repository.RealisateursFilmsRepository;

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
  private final CategorieFilmsRepository categorieFilmsRepository;

  /**
   * Le constructeur
   * @param filmRepository Injection du repository
   */
  public FilmsService(FilmsRepository filmRepository, ActeursFilmsRepository acteursFilmsRepository, RealisateursFilmsRepository realisateursFilmsRepository, CategorieFilmsRepository categorieFilmsRepository) {
    this.filmRepository = filmRepository;
    this.acteursFilmsRepository = acteursFilmsRepository;
    this.realisateursFilmsRepository = realisateursFilmsRepository;
    this.categorieFilmsRepository = categorieFilmsRepository;
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
   * Récupérer les 4 films les plus récemment sortis
   *
   * @return les 4 films avec les dates de sortie les plus récentes
   */
  public List<Films> findLatestFilms() {
    return filmRepository.findTop4ByOrderByDateSortieDesc();
  }

  /**
   * Récupérer un film et toutes ses informations détaillées
   * @param id L'id du film
   * @return Le détail complet d'un film
   */
  public FilmDetailDTO findDetailById(Long id) {

    Films film = filmRepository.findById(id)
      .orElseThrow(() -> new CustomException("Films", "id", id));

    List<RealisateursFilms> realisateursFilms =
      realisateursFilmsRepository.findByFilmsId(id);

    List<ActeursFilms> acteursFilms =
      acteursFilmsRepository.findByFilmsId(id);

    List<CategorieFilms> categorieFilms =
      categorieFilmsRepository.findByFilmsId(id);

    String realisateur = realisateursFilms.stream()
      .map(rf ->
        rf.getRealisateurs().getPrenom() + " " +
          rf.getRealisateurs().getNom()
      )
      .findFirst()
      .orElse("");

    List<ActeurDetailDTO> acteurs = acteursFilms.stream()
      .map(af -> new ActeurDetailDTO(
        af.getActeurs().getNom(),
        af.getActeurs().getPrenom(),
        af.getRole()
      ))
      .toList();

    List<String> categories = categorieFilms.stream()
      .map(cf -> cf.getCategorie().getCategorie())
      .toList();

    return new FilmDetailDTO(
      film,
      realisateur,
      acteurs,
      categories
    );
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
      existingFilm.setResumeLong(film.getResumeLong());
      existingFilm.setDuree(film.getDuree());
      existingFilm.setImage(film.getImage());
      existingFilm.setNoteMoyenne(film.getNoteMoyenne());
      return filmRepository.save(existingFilm);
    } else {
      throw new CustomException("Le film n'existe pas", "id", film.getId());
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
