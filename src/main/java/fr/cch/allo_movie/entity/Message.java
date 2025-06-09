package fr.cch.allo_movie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "message")
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_message")
  private Long id;

  @Column(name = "date_message", nullable = false)
  private LocalDateTime dateMessage;

  @Column(name = "message", nullable = false, length = 500)
  private String message;

  @ManyToOne
  @JoinColumn(name = "id_user", nullable = false)
  private Users userMessage;

  public Message(LocalDateTime dateMessage, String message, Users user) {
    this.dateMessage = dateMessage;
    this.message = message;
    this.userMessage = user;
  }

  @Override
  public String toString() {
    return "Message{" +
      "id=" + id +
      ", dateMessage=" + dateMessage +
      ", message='" + message + '\'' +
      ", user=" + userMessage +
      '}';
  }

}
