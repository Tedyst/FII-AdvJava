package ro.tedyst.lab7.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.SecurityContext;
import org.primefaces.component.password.Password;
import ro.tedyst.lab7.model.MyUser;
import ro.tedyst.lab7.model.UserGroup;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class AuthBean implements Serializable {
    private String username;
    private String password;
    private MyUser currentMyUser;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private HttpServletRequest request;

    public String login() {
        try {
            request.login(username, password);
            currentMyUser = em.find(MyUser.class, username);
            return "index.xhtml?faces-redirect=true";
        } catch (ServletException | RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return "login.xhtml?faces-redirect=true";
    }

    public String logout() {
        try {
            ExternalContext externalContext =
                    FacesContext.getCurrentInstance().getExternalContext();
            HttpServletRequest request = (HttpServletRequest)
                    externalContext.getRequest();
            request.logout();
            externalContext.invalidateSession();
            externalContext.log("Logout: " + username);
        } catch (ServletException ex) {
            System.err.println(ex.getMessage());
        }
        return "login.xhtml?faces-redirect=true";
    }

    public boolean hasPermission(String group) {
        List<UserGroup> groups = em.createQuery("SELECT ug FROM UserGroup ug WHERE ug.name = :USERNAME", UserGroup.class).setParameter("USERNAME", username).getResultList();

        for (UserGroup ug : groups) {
            if (ug.getName().equals(group)) {
                return true;
            }
        }

        return false;
    }

    // Getters and Setters

    public MyUser getCurrentMyUser() {
        return currentMyUser;
    }

    public void setCurrentMyUser(MyUser currentMyUser) {
        this.currentMyUser = currentMyUser;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
