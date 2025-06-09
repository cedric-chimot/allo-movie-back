package fr.cch.allo_movie.service;

import fr.cch.allo_movie.entity.Messages;
import fr.cch.allo_movie.entity.Users;
import fr.cch.allo_movie.exceptions.CustomException;
import fr.cch.allo_movie.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

  private final MessageRepository messageRepository;
  private final UsersService usersService;

  /**
   * Le constructeur du service
   * @param messageRepository injection du répo message
   * @param usersService injection du répo users
   */
  public MessageService(MessageRepository messageRepository, UsersService usersService) {
    this.messageRepository = messageRepository;
    this.usersService = usersService;
  }

  /**
   * Ajouter un nouveau message
   * @param content le contenu du message
   * @param userId l'id de l'utilisateur envoyant le message
   * @return le nouveau message
   */
  public Messages saveMessage(String content, Long userId) {
    Users user = usersService.findById(userId);

    Messages messages = new Messages();
    messages.setMessage(content);
    messages.setUserMessage(user);
    messages.setDateMessage(LocalDateTime.now());

    return messageRepository.save(messages);
  }

  /**
   * Méthode pour trouver tous les messages
   * @return la liste des messages
   */
  public List<Messages> getAllMessages() {
    return messageRepository.findAll();
  }

  /**
   * Récupérer un message par ID
   * @param id l'id du message recherché
   * @return L'objet trouvé
   */
  public Messages getMessageById(Long id) {
    return messageRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Message non trouvé"));
  }

  /**
   * Mettre à jour un message
   * @param id l'id du message
   * @param content le contenu du message
   * @return le message mis à jour
   */
  public Messages updateMessage(Long id, String content) {
    Messages existingMessages = messageRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Message non trouvé"));

    existingMessages.setMessage(content);
    existingMessages.setDateMessage(LocalDateTime.now());

    return messageRepository.save(existingMessages);
  }

  /**
   * Supprimer un message par son id
   * @param id l'id du message à supprimer
   * @return L'objet supprimé
   */
  public Messages deleteById(Long id) {
    // Récupérer l'objet dans un Optional
    Optional<Messages> optionalUsers = messageRepository.findById(id);

    // Vérifier si l'objet existe
    if (optionalUsers.isPresent()) {
      Messages messages = optionalUsers.get();
      messageRepository.delete(messages); // Supprimer l'objet
      return messages; // Retourner l'objet supprimé
    } else {
      throw new CustomException("Message", "id",  id);
    }
  }

  /**
   * Supprimer tous les messages
   */
  public void deleteAll() {
    messageRepository.deleteAll();
  }

}
