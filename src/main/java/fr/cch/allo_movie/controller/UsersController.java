package fr.cch.allo_movie.controller;

import fr.cch.allo_movie.entity.Users;
import fr.cch.allo_movie.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UsersController {

  /**
   * Le service des utilisateurs
   */
  private final UsersService userService;

  /**
   * Le constructeur
   * @param userService Injection du service
   */
  public UsersController(UsersService userService) {
    this.userService = userService;
  }

  /**
   * Ajouter un nouvel utilisateur
   * @param user l'utilisateur à ajouter
   * @return l'utilisateur nouvellement ajouté
   */
  @PostMapping("/create")
  public ResponseEntity<Users> saveUser(@RequestBody Users user) {
    Users userSave = userService.save(
      user.getPseudo(),
      user.getEmail(),
      user.getMdp(),
      user.getRole().getId()
    );
    return ResponseEntity.ok(userSave);
  }

  /**
   * Afficher la liste de tous les utilisateurs
   * @return la liste des utilisateurs
   */
  @GetMapping("/all")
  public List<Users> findAllUsers() {
    return userService.findAll();
  }

  /**
   * Trouver un utilisateur par son id
   * @param id l'id d'un utilisateur
   * @return l'utilisateur recherché
   */
  @GetMapping("/{id}")
  public Users findById(@PathVariable Long id) {
    return userService.findById(id);
  }

  /**
   * Compter le nombre d'utilisateurs
   * @return le nombre d'utilisateurs
   */
  @GetMapping("/count")
  public Long getCountUsers() {
    return userService.findUsersCount();
  }

  /**
   * Mettre à jour un utilisateur
   * @param user l'utilisateur à mettre à jour
   * @return l'utilisateur mis à jour
   */
  @PutMapping("/update")
  public Users updateUser(@RequestBody Users user) {
    return userService.updateUser(user);
  }

  /**
   * Supprimer un utilisateur par son id
   * @param id l'id de l'utilisateur à supprimer
   * @return l'utilisateur supprimé
   */
  @DeleteMapping("/delete/{id}")
  public Users deleteUserById(@PathVariable Long id) {
    return userService.deleteById(id);
  }

  /**
   * Supprimer tous les utilisateurs
   */
  @DeleteMapping("/delete/all")
  public void deleteAllUsers() {
    userService.deleteAll();
  }

}
