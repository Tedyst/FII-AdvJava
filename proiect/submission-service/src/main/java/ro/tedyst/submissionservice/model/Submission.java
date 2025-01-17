package ro.tedyst.submissionservice.model;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Submission {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String password;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name = "user_group_mapping", // Name of the join table
            joinColumns = @JoinColumn(name = "user_id"), // Foreign key in join table pointing to User
            inverseJoinColumns = @JoinColumn(name = "group_id") // Foreign key in join table pointing to UserGroup
    )
    private Set<UserGroup> groups = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordHashed(String password) {
        Argon2 argon2 = Argon2Factory.create();
        this.password = argon2.hash(10, 65536, 1, password);
    }

    public boolean verifyPassword(String password) {
        Argon2 argon2 = Argon2Factory.create();
        return argon2.verify(this.password, password);
    }

    public void setGroups(Set<UserGroup> groups) {
        this.groups = groups;
    }

    public Set<UserGroup> getGroups() {
        return groups;
    }
}
