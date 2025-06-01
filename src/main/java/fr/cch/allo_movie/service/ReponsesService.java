package fr.cch.allo_movie.service;

import fr.cch.allo_movie.entity.Reponses;
import fr.cch.allo_movie.exceptions.CustomException;
import fr.cch.allo_movie.repository.ReponsesRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ReponsesService {


  private final ReponsesRepository reponsesRepository;

  public ReponsesService(ReponsesRepository reponsesRepository) {
    this.reponsesRepository = reponsesRepository;
  }

  /**
   * Méthode pour trouver une réponse par son id
   * @param id l'id de la réponse recherchée
   * @return la réponse trouvée
   */
  public Reponses findById(Long id) {
    return reponsesRepository.findById(id)
      .orElseThrow(() -> new CustomException("Reponses", "id", id));
  }

}
