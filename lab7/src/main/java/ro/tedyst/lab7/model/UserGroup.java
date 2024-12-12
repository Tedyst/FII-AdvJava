package ro.tedyst.lab7.model;
import jakarta.persistence.*;

@Entity
public class UserGroup {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String username;

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(long id) {
        this.id = id;
    }
}
