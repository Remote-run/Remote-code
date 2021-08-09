package no.ntnu.remotecode.master.health;


import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Endpoints used by Kubernetes Health Checking
 */
@Readiness
@ApplicationScoped
public class MasterReadinessCheck implements HealthCheck {

    private static final String readinessCheck = "Auth Service Readiness Check";


    public HealthCheckResponse call() {
        if (isSystemServiceReachable()) {
            return HealthCheckResponse.up(readinessCheck);
        } else {
            return HealthCheckResponse.down(readinessCheck);
        }
    }

    private boolean isSystemServiceReachable() {
        try {
            Client client = ClientBuilder.newClient();
            client.target("http://localhost:9080/projects/ping").request().get();

            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
