package ro.tedyst.lab7.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Any;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ro.tedyst.lab7.model.Activity;
import ro.tedyst.lab7.model.Submission;
import jakarta.enterprise.event.Event;
import ro.tedyst.lab7.model.MyUser;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named
@Logged
@SessionScoped
public class SubmissionBean implements Serializable {
    @PersistenceContext
    private EntityManager em;

    @Any
    private Event<Submission> submissionsEvent;

    @Inject
    @MyQualifier
    private String registrationNumber;

    @Inject
    private AuthBean authBean;

    private List<Activity> activities;
    private Submission submission = new Submission();
    private Date rangeStart;
    private Date rangeEnd;

    public void loadActivities() {
        activities = em.createQuery("select a from Activity a", Activity.class).getResultList();
    }

    @Transactional
    public String submit() {
        submission.setCreatedAt(new Date());
        submission.setRegistrationNumber(registrationNumber);
        em.persist(submission);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Evaluation submitted successfully"));
        submissionsEvent.fire(submission);
        return null;
    }

    public Submission getSubmission() {
        return submission;
    }

    public Date getRangeEnd() {
        return rangeEnd;
    }

    public Date getRangeStart() {
        return rangeStart;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    public void setRangeEnd(Date rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    public void setRangeStart(Date rangeStart) {
        this.rangeStart = rangeStart;
    }

    public List<Submission> getEvaluations() {
        if (authBean.getCurrentMyUser().getUserType() == MyUser.UserType.ADMIN) {
            return em.createQuery("SELECT s from Submission s", Submission.class).getResultList();
        } else if (authBean.getCurrentMyUser().getUserType() == MyUser.UserType.TEACHER) {
            return em.createQuery("SELECT s from Submission s WHERE s.activity.teacher = :TEACHER", Submission.class).setParameter("TEACHER", authBean.getCurrentMyUser()).getResultList();
        } else {
            return em.createQuery("SELECT s FROM Submission s where s.myUser = :USER", Submission.class).setParameter("USER", authBean.getCurrentMyUser()).getResultList();
        }
    }
}
