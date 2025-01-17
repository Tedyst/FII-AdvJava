package ro.tedyst.submissionservice.dto;

public class CreateUser {
    public String username;
    public String password;

    // Ensure getters and setters are present
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
