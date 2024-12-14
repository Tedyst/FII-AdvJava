package ro.tedyst.lab10.resilient;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.eclipse.microprofile.faulttolerance.*;

@Path("/resilience")
@ApplicationScoped
public class ResilientService {
    @GET
    @Path("/do-something")
    @Timeout(2000)
    @Retry(maxRetries = 3, delay = 1000)
    @Fallback(fallbackMethod = "fallbackMethod")
    public String doSomething() {
        simulateDelay();
        return "Success";
    }

    public String fallbackMethod() {
        return "Fallback Response";
    }

    @GET
    @Path("/circuit-breaker")
    @CircuitBreaker(failureRatio = 0.5, delay = 5000, successThreshold = 2)
    public String circuitBreakerMethod() {
        simulateFailure();
        return "This should fail";
    }

    @GET
    @Path("/bulkhead-threadpool")
    @Bulkhead(value = 5, waitingTaskQueue = 10) // Max 5 threads and 10 waiting tasks
    public String bulkheadThreadPool() {
        simulateDelay();
        return "Bulkhead Thread-Pool Success";
    }

    @GET
    @Path("/bulkhead-semaphore")
    @Bulkhead(value = 3)
    public String bulkheadSemaphore() {
        simulateDelay();
        return "Bulkhead Semaphore Success";
    }

    private void simulateDelay() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void simulateFailure() {
        double random = Math.random();
        if (random < 0.7) {
            throw new RuntimeException("Simulated Failure");
        }
    }
}