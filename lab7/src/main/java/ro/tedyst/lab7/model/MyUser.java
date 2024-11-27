package ro.tedyst.lab7.model;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.*;


@Entity
public class MyUser {
    @Id
    @GeneratedValue
    private long id;

    @Column(unique=true)
    private String name;
    private String passwordHash;

    public enum UserType {
        STUDENT,
        TEACHER,
        ADMIN
    };

    private UserType userType;

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        Argon2 argon2 = Argon2Factory.create();
        passwordHash = argon2.hash(22, 65536, 1, password.toCharArray());
    }

    public boolean verifyPassword(String password) {
        Argon2 argon2 = Argon2Factory.create();
        return argon2.verify(passwordHash, password.toCharArray());
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", userType=" + userType +
                '}';
    }
}
