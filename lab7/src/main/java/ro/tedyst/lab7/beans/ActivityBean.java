package ro.tedyst.lab7.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.annotation.View;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ro.tedyst.lab7.model.Activity;
import ro.tedyst.lab7.model.MyUser;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ActivityBean implements Serializable {
    private Activity newActivity = new Activity();

    @PersistenceContext
    private EntityManager em;

    public void addActivity() {
        System.out.println("addActivity" + newActivity + " " + newActivity.getTeacher());
        try {
            em.persist(newActivity);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Activity added successfully!", null));
            newActivity = new Activity(); // Reset form
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error adding activity: " + e.getMessage(), null));
        }
    }

    public void deleteActivity(Activity activity) {
        try {
            em.remove(em.merge(activity));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Activity deleted successfully!", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error deleting activity: " + e.getMessage(), null));
        }
    }

    public List<Activity> getActivities() {
        return em.createQuery("select a from Activity a", Activity.class).getResultList();
    }

    public List<MyUser> getTeachers() {
        return em.createQuery("select u from MyUser u WHERE u.userType = :TYPE", MyUser.class).setParameter("TYPE", MyUser.UserType.TEACHER).getResultList();
    }

    public Activity getNewActivity() {
        return newActivity;
    }

    public void setNewActivity(Activity newActivity) {
        this.newActivity = newActivity;
    }
}
