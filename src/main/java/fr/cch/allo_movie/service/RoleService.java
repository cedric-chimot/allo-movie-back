package fr.cch.allo_movie.service;

import fr.cch.allo_movie.entity.Role;
import fr.cch.allo_movie.exceptions.CustomException;
import fr.cch.allo_movie.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RoleService {

  private final RoleRepository roleRepository;

  public RoleService(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  /**
   * Méthode pour trouver un rôle par son id
   * @param id l'id du rôle recherché
   * @return le rôle trouvé
   */
  public Role findById(Long id) {
    return roleRepository.findById(id)
      .orElseThrow(() -> new CustomException("Role", "id", id));
  }

}
