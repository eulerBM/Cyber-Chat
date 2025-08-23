package chat.cyber.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table(name = "users")
@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private UUID idPublic;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 300, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @ManyToMany(mappedBy = "users",
            cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY,
            targetEntity = Chat.class)
    private Set<Chat> chat = new HashSet<>();

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.idPublic = UUID.randomUUID();
    }

    @PrePersist
    public void generateIdPublic(){

        if (idPublic == null){
            idPublic = UUID.randomUUID();
        }
    }

    public User() {}

    public long getId() {
        return id;
    }

    public UUID getIdPublic() {
        return idPublic;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
