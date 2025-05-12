package fr.cch.allo_movie.service;

import fr.cch.allo_movie.entity.Categorie;
import fr.cch.allo_movie.exceptions.CustomException;
import fr.cch.allo_movie.repository.CategorieRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategorieService {

  /**
   * Le repository des catégories
   */
  private final CategorieRepository categorieRepository;

  /**
   * Le constructeur
   * @param categorieRepository Injection du repository
   */
  public CategorieService(CategorieRepository categorieRepository) {
    this.categorieRepository = categorieRepository;
  }

  /**
   * Méthode pour ajouter une catégorie
   * @return la catégorie ajoutée
   */
  public Categorie save(Categorie categorie) {
    return categorieRepository.save(categorie);
  }

  /**
   * Méthode pour trouver toutes les catégories
   * @return la liste des catégories
   */
  public List<Categorie> findAll() {
    return categorieRepository.findAll();
  }

  /**
   * Méthode pour trouver une catégorie par son id
   * @param id l'id de la catégorie recherchée
   * @return la catégorie trouvée
   */
  public Categorie findById(Long id) {
    return categorieRepository.findById(id)
      .orElseThrow(() -> new CustomException("Categorie", "id", id));
  }

  /**
   * Mettre à jour une catégorie
   * @param categorie L'objet à mettre à jour
   * @return L'objet mis à jour
   */
  public Categorie updateCategorie(Categorie categorie) {
    Optional<Categorie> isCategorieExist= categorieRepository.findById(categorie.getId());

    if (isCategorieExist.isPresent()) {
      Categorie existingCategorie = isCategorieExist.get();

      existingCategorie.setCategorie(categorie.getCategorie());
      return categorieRepository.save(existingCategorie);
    } else {
      throw new CustomException("La catégorie n'existe pas", "id", categorie.getId());
    }
  }

  /**
   * Méthode pour supprimer une catégorie par son Id
   * @param id L'identifiant de la catégorie à supprimer
   * @return L'objet supprimé
   */
  public Categorie deleteById(Long id) {
    // Récupérer l'objet dans un Optional
    Optional<Categorie> optionalCategorie = categorieRepository.findById(id);

    // Vérifier si l'objet existe
    if (optionalCategorie.isPresent()) {
      Categorie categorie = optionalCategorie.get();
      categorieRepository.delete(categorie); // Supprimer l'objet
      return categorie; // Retourner l'objet supprimé
    } else {
      throw new CustomException("Categorie", "id",  id);
    }
  }

  /**
   * Supprimer toutes les catégories
   */
  public void deleteAll() {
    categorieRepository.deleteAll();
  }

}
