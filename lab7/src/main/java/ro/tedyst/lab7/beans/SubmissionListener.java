package ro.tedyst.lab7.beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import ro.tedyst.lab7.model.Submission;

@ApplicationScoped
public class SubmissionListener {
    public void onAnySubmissionEvent(@Observes Submission submission) {
        System.out.println("Observed creating a submission " + submission.toString());
    }
}
