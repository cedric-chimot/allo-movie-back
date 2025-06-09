package fr.cch.allo_movie.controller;

import fr.cch.allo_movie.entity.Messages;
import fr.cch.allo_movie.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@CrossOrigin
public class MessageController {

  /**
   * Le service des messages
   */
  private final MessageService messageService;

  /**
   * Le constructeur
   * @param messageService injection du service message
   */
  public MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  /**
   * Créer un nouveau message
   * @param content le contenu du message
   * @param userId l'id de l'utilisateur
   * @return le nouveau message
   */
  @PostMapping("/create")
  public ResponseEntity<Messages> createMessage(@RequestParam String content, @RequestParam Long userId) {
    Messages messages = messageService.saveMessage(content, userId);
    return ResponseEntity.ok(messages);
  }

  /**
   * Afficher la liste de tous les messages
   * @return la liste des messages
   */
  @GetMapping("/all")
  public ResponseEntity<List<Messages>> getAllMessages() {
    return ResponseEntity.ok(messageService.getAllMessages());
  }

  /**
   * Trouver un message par son id
   * @param id l'id d'un message
   * @return le message recherché
   */
  @GetMapping("/{id}")
  public ResponseEntity<Messages> getMessageById(@PathVariable Long id) {
    return ResponseEntity.ok(messageService.getMessageById(id));
  }

  /**
   * Mettre à jour un message
   * @param id l'id du message à mettre à jour
   * @param content le contenu à mettre à jour
   * @return le message mis à jour
   */
  @PatchMapping("/update/{id}")
  public ResponseEntity<Messages> updateMessage(@PathVariable Long id, @RequestParam String content) {
    Messages updatedMessages = messageService.updateMessage(id, content);
    return ResponseEntity.ok(updatedMessages);
  }

  /**
   * Supprimer un message par son id
   * @param id l'id de le message à supprimer
   * @return le message supprimé
   */
  @DeleteMapping("/delete/{id}")
  public Messages deleteMessageById(@PathVariable Long id) {
    return messageService.deleteById(id);
  }

  /**
   * Supprimer tous les messages
   */
  @DeleteMapping("/delete/all")
  public void deleteAllMessages() {
    messageService.deleteAll();
  }

}
