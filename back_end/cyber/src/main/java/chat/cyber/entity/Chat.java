package chat.cyber.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private UUID idPublic;

    @Column(nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinTable(name = "chat_users",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "chat", orphanRemoval = true, fetch = FetchType.EAGER, targetEntity = Message.class)
    @OrderBy("timestamp ASC")
    private List<Message> messages;


    @PrePersist
    public void generateIdPublic(){

        if (idPublic == null){

            idPublic = UUID.randomUUID();
        }
    }

    public Chat(String name, Set<User> users, List<Message> messages) {
        this.name = name;
        this.users = users;
        this.messages = messages;
    }

    public Chat(String name, Set<User> users) {
        this.name = name;
        this.users = users;
    }

    public Chat() {
    }

    public long getId() {
        return id;
    }

    public UUID getIdPublic() {
        return idPublic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
