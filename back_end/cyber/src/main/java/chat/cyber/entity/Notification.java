package chat.cyber.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long receiverId;
    private Long senderId;
    private String type;       // "MESSAGE", etc
    private String content;
    private boolean read = false;

    private LocalDateTime timestamp = LocalDateTime.now();
}
