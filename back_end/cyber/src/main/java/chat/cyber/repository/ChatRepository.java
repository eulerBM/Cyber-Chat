package chat.cyber.repository;

import chat.cyber.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;


public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT c FROM Chat c " +
            "JOIN c.users u " +
            "WHERE u.id IN (:user1, :user2) " +
            "GROUP BY c.id " +
            "HAVING COUNT(c) = 2")
    Optional<Chat> findByUsers(@Param("user1") Long user1, @Param("user2") Long user2);

    Optional<Chat> findByIdPublic(UUID idPublic);



}
