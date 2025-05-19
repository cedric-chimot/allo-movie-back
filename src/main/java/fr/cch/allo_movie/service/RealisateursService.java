package fr.cch.allo_movie.service;

import fr.cch.allo_movie.entity.Realisateurs;
import fr.cch.allo_movie.exceptions.CustomException;
import fr.cch.allo_movie.repository.RealisateursRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RealisateursService {

  /**
   * Le repository des réalisateurs
   */
  private final RealisateursRepository realisateurRepository;

  /**
   * Le constructeur
   * @param realisateurRepository Injection du repository
   */
  public RealisateursService(RealisateursRepository realisateurRepository) {
    this.realisateurRepository = realisateurRepository;
  }

  /**
   * Méthode pour ajouter un réalisateur
   * @param realisateurs le réalisateur à ajouter
   * @return le nouveau réalisateur
   */
  public Realisateurs save(Realisateurs realisateurs) {
    return realisateurRepository.save(realisateurs);
  }

  /**
   * Méthode pour trouver tous les réalisateurs
   * @return la liste des réalisateurs
   */
  public List<Realisateurs> findAll() {
    return realisateurRepository.findAll();
  }

  /**
   * Méthode pour trouver un réalisateur par son id
   * @param id l'id du réalisateur recherché
   * @return le réalisateur trouvé
   */
  public Realisateurs findById(Long id) {
    return realisateurRepository.findById(id)
      .orElseThrow(() -> new CustomException("Realisateurs", "id", id)); // Renvoie l'entité
  }

  /**
   * Méthode pour retrouver le nombre total de réalisateurs
   * @return le nombre de réalisateurs
   */
  public Long findRealisateursCount() {
    return realisateurRepository.countRealisateurs();
  }

  /**
   * Mettre à jour un réalisateur
   * @param realisateur L'objet à mettre à jour
   * @return L'objet mis à jour
   */
  public Realisateurs updateRealisateur(Realisateurs realisateur) {
    Optional<Realisateurs> isRealisateurExist= realisateurRepository.findById(realisateur.getId());

    if (isRealisateurExist.isPresent()) {
      Realisateurs existingRealisateur = isRealisateurExist.get();

      existingRealisateur.setNom(realisateur.getNom());
      existingRealisateur.setPrenom(realisateur.getPrenom());
      return realisateurRepository.save(existingRealisateur);
    } else {
      throw new CustomException("L'réalisateur n'existe pas", "id", realisateur.getId());
    }
  }

  /**
   * Méthode pour supprimer un réalisateur par son Id
   * @param id L'identifiant du réalisateur à supprimer
   * @return L'objet supprimé
   */
  public Realisateurs deleteById(Long id) {
    // Récupérer l'objet dans un Optional
    Optional<Realisateurs> optionalRealisateurs = realisateurRepository.findById(id);

    // Vérifier si l'objet existe
    if (optionalRealisateurs.isPresent()) {
      Realisateurs realisateur = optionalRealisateurs.get();
      realisateurRepository.delete(realisateur); // Supprimer l'objet
      return realisateur; // Retourner l'objet supprimé
    } else {
      throw new CustomException("Realisateurs", "id",  id);
    }
  }

  /**
   * Supprimer tous les réalisateurs
   */
  public void deleteAll() {
    realisateurRepository.deleteAll();
  }

}
