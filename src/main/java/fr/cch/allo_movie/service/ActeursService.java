package fr.cch.allo_movie.service;

import fr.cch.allo_movie.entity.Acteurs;
import fr.cch.allo_movie.exceptions.CustomException;
import fr.cch.allo_movie.repository.ActeursRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ActeursService {

  /**
   * Le repository des acteurs
   */
  private final ActeursRepository acteurRepository;

  /**
   * Le constructeur
   * @param acteurRepository Injection du repository
   */
  public ActeursService(ActeursRepository acteurRepository) {
    this.acteurRepository = acteurRepository;
  }

  /**
   * Méthode pour ajouter un acteur
   * @return l'acteur ajouté
   */
  public Acteurs save(Acteurs acteur) {
    return acteurRepository.save(acteur);
  }

  /**
   * Méthode pour trouver tous les acteurs
   * @return la liste des acteurs
   */
  public List<Acteurs> findAll() {
    return acteurRepository.findAll();
  }

  /**
   * Méthode pour trouver un acteur par son id (retourne l'entité complète)
   * @param id l'id de l'acteur recherché
   * @return l'acteur trouvé
   */
  public Acteurs findById(Long id) {
    return acteurRepository.findById(id)
      .orElseThrow(() -> new CustomException("Acteurs", "id", id)); // Renvoie l'entité
  }

  /**
   * Mettre à jour un acteur
   * @param acteur L'objet à mettre à jour
   * @return L'objet mis à jour
   */
  public Acteurs updateActeur(Acteurs acteur) {
    Optional<Acteurs> isActeurExist= acteurRepository.findById(acteur.getId());

    if (isActeurExist.isPresent()) {
      Acteurs existingActeur = isActeurExist.get();

      existingActeur.setNom(acteur.getNom());
      existingActeur.setPrenom(acteur.getPrenom());
      return acteurRepository.save(existingActeur);
    } else {
      throw new CustomException("L'acteur n'existe pas", "id", acteur.getId());
    }
  }

  /**
   * Méthode pour supprimer un acteur par son Id
   * @param id L'identifiant de l'acteur à supprimer
   * @return L'objet supprimé
   */
  public Acteurs deleteById(Long id) {
    // Récupérer l'objet dans un Optional
    Optional<Acteurs> optionalActeurs = acteurRepository.findById(id);

    // Vérifier si l'objet existe
    if (optionalActeurs.isPresent()) {
      Acteurs acteur = optionalActeurs.get();
      acteurRepository.delete(acteur); // Supprimer l'objet
      return acteur; // Retourner l'objet supprimé
    } else {
      throw new CustomException("Acteurs", "id",  id);
    }
  }

  /**
   * Supprimer tous les acteurs
   */
  public void deleteAll() {
    acteurRepository.deleteAll();
  }

}
