package fr.cch.allo_movie.service;

import fr.cch.allo_movie.entity.Message;
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
  public Message saveMessage(String content, Long userId) {
    Users user = usersService.findById(userId);

    Message message = new Message();
    message.setMessage(content);
    message.setUserMessage(user);
    message.setDateMessage(LocalDateTime.now());

    return messageRepository.save(message);
  }

  /**
   * Méthode pour trouver tous les messages
   * @return la liste des messages
   */
  public List<Message> getAllMessages() {
    return messageRepository.findAll();
  }

  /**
   * Récupérer un message par ID
   * @param id l'id du message recherché
   * @return L'objet trouvé
   */
  public Message getMessageById(Long id) {
    return messageRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Message non trouvé"));
  }

  /**
   * Mettre à jour un message
   * @param id l'id du message
   * @param content le contenu du message
   * @return le message mis à jour
   */
  public Message updateMessage(Long id, String content) {
    Message existingMessage = messageRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Message non trouvé"));

    existingMessage.setMessage(content);
    existingMessage.setDateMessage(LocalDateTime.now());

    return messageRepository.save(existingMessage);
  }

  /**
   * Supprimer un message par son id
   * @param id l'id du message à supprimer
   * @return L'objet supprimé
   */
  public Message deleteById(Long id) {
    // Récupérer l'objet dans un Optional
    Optional<Message> optionalUsers = messageRepository.findById(id);

    // Vérifier si l'objet existe
    if (optionalUsers.isPresent()) {
      Message message = optionalUsers.get();
      messageRepository.delete(message); // Supprimer l'objet
      return message; // Retourner l'objet supprimé
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
