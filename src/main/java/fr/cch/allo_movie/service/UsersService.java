package fr.cch.allo_movie.service;

import fr.cch.allo_movie.entity.Role;
import fr.cch.allo_movie.entity.Users;
import fr.cch.allo_movie.exceptions.CustomException;
import fr.cch.allo_movie.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsersService {

  /**
   * Le repository des utilisateurs
   */
  private final UsersRepository userRepository;

  /**
   * Le service des rôles
   */
  private final RoleService roleService;

  /**
   * Le constructeur
   * @param userRepository Injection du repository
   */
  public UsersService(UsersRepository userRepository, RoleService roleService) {
    this.userRepository = userRepository;
    this.roleService = roleService;
  }

  /**
   * Méthode pour ajouter un utilisateur
   * @param pseudo son pseudo
   * @param email son mail
   * @param mdp son mot de passe
   * @return l'utilisateur ajouté
   */
  public Users save(String pseudo, String email, String mdp) {
    Role role = roleService.findById(2L);

    String hashedPassword = BCrypt.hashpw(mdp, BCrypt.gensalt());

    LocalDateTime dateInscrit = LocalDateTime.now(); // date et heure actuelles

    Users user = new Users(pseudo, email, hashedPassword, role, dateInscrit);

    return userRepository.save(user);
  }

  /**
   * Authentification d'un utilisateur
   *
   * @param email l'email de l'utilisateur
   * @param mdp le mot de passe fourni
   * @return l'utilisateur authentifié
   * @throws CustomException si l'utilisateur n'existe pas ou si le mot de passe est incorrect
   */
  public Users login(String email, String mdp) {
    Users user = findByEmail(email);
    if (user == null) return null;

    // Vérifier le mot de passe avec BCrypt
    if (!BCrypt.checkpw(mdp, user.getMdp())) {
      return null; // mot de passe incorrect
    }

    return user; // connexion réussie
  }

  /**
   * Trouver un utilisateur par son email
   * @param email l'email de l'utilisateur recherché
   * @return l'utilisateur trouvé
   */
  public Users findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  /**
   * Méthode pour trouver tous les utilisateurs
   * @return la liste des utilisateurs
   */
  public List<Users> findAll() {
    return userRepository.findAll();
  }

  /**
   * Méthode pour trouver un utilisateur par son id (retourne l'entité complète)
   * @param id l'id de l'utilisateur recherché
   * @return l'utilisateur trouvé
   */
  public Users findById(Long id) {
    return userRepository.findById(id)
      .orElseThrow(() -> new CustomException("Users", "id", id)); // Renvoie l'entité
  }

  /**
   * Méthode pour retrouver le nombre total d'utilisateurs
   * @return le nombre d'utilisateurs
   */
  public Long findUsersCount() {
    return userRepository.countUsers();
  }

  /**
   * Mettre à jour un utilisateur
   * @param user L'objet à mettre à jour
   * @return L'objet mis à jour
   */
  public Users updateUser(Users user) {
    Optional<Users> isUserExist = userRepository.findById(user.getId());

    if (isUserExist.isPresent()) {
      Users existingUser = isUserExist.get();

      existingUser.setPseudo(user.getPseudo());
      existingUser.setEmail(user.getEmail());

      if (user.getMdp() != null && !user.getMdp().isBlank()) {
        String hashedPassword = BCrypt.hashpw(user.getMdp(), BCrypt.gensalt());
        existingUser.setMdp(hashedPassword);
      }

      existingUser.setPresentation(user.getPresentation());
      existingUser.setRole(user.getRole());

      return userRepository.save(existingUser);
    } else {
      throw new CustomException("L'utilisateur n'existe pas", "id", user.getId());
    }
  }

  /**
   * Méthode pour supprimer un utilisateur par son Id
   * @param id L'identifiant de l'utilisateur à supprimer
   * @return L'objet supprimé
   */
  public Users deleteById(Long id) {
    // Récupérer l'objet dans un Optional
    Optional<Users> optionalUsers = userRepository.findById(id);

    // Vérifier si l'objet existe
    if (optionalUsers.isPresent()) {
      Users user = optionalUsers.get();
      userRepository.delete(user); // Supprimer l'objet
      return user; // Retourner l'objet supprimé
    } else {
      throw new CustomException("Users", "id",  id);
    }
  }

  /**
   * Supprimer tous les utilisateurs
   */
  public void deleteAll() {
    userRepository.deleteAll();
  }

}
