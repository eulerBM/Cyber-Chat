package chat.cyber.repository;

import chat.cyber.entity.Message;
import chat.cyber.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
