package no.woldseth.auth;


import no.woldseth.auth.model.AuthenticatedUser;
import no.woldseth.auth.model.DTO.NewAuthUserData;
import no.woldseth.auth.model.Group;
import no.woldseth.auth.control.AuthenticationService;


import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Manages the startup actions for the Authentication service.
 * This manly includes generating the different admin and management user needed for the cluser to work
 */
@Singleton
@Startup
public class AuthenticationStartup {

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    AuthenticationService authenticationService;

    /**
     * The admin user username
     */
    @Inject
    @ConfigProperty(name = "woldseth.service.adminUsername", defaultValue = "admin_default")
    private String adminUsername;

    /**
     * The admin user password
     */
    @Inject
    @ConfigProperty(name = "woldseth.service.adminPassword", defaultValue = "woldseth")
    private String adminPassword;


    /**
     * starts the async tasks generate the users and groups.
     */
    @PostConstruct
    @Asynchronous
    public void initialize() {
        this.persistUserGroups();
        this.createIfAbsent(adminUsername, adminPassword, List.of(Group.ADMIN_GROUP_NAME));
    }

    /**
     * Validates that the database contains all the {@link Group} names. If not, any missing name is added.
     */
    public void persistUserGroups() {
        long groups = (long) entityManager.createQuery("SELECT count(g.name) from Group g").getSingleResult();
        if (groups != Group.GROUPS.length) {
            for (String group : Group.GROUPS) {
                entityManager.persist(new Group(group));
            }
        }
    }


    public void createIfAbsent(String username, String password, List<String> groops) {
        Optional<AuthenticatedUser> user = authenticationService.getUserFromPrincipal(username);

        if (user.isEmpty()) {
            var newAuthUserData = new NewAuthUserData();
            newAuthUserData.setUserName(username);
            newAuthUserData.setPassword(password);
            newAuthUserData.setGroups(groops);
            var authUser = authenticationService.createUser(newAuthUserData);
        }
    }


}