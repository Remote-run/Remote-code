package no.woldseth.auth;


import lombok.Data;
import lombok.NoArgsConstructor;
import no.woldseth.auth.exceptions.InvalidConfigException;
import no.woldseth.auth.model.AuthenticatedUser;
import no.woldseth.auth.model.DTO.NewAuthUserData;
import no.woldseth.auth.model.Group;
import no.woldseth.auth.control.AuthenticationService;


import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

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

    private static final String defaultUserConfigPrefix = "DEFAULT_USER_";


    /**
     * starts the async tasks generate the users and groups.
     */
    @PostConstruct
    @Asynchronous
    public void initialize() {
        this.persistUserGroups();
        List<DefaultUser> defaultUsers = getDefaultUsers();
        defaultUsers.forEach(this::createIfAbsent);
    }

    private List<DefaultUser> getDefaultUsers() {
        Map<String, String>    env     = System.getenv();
        ArrayList<DefaultUser> retList = new ArrayList<>();

        env.forEach((envName, envValue) -> {
            if (envName.startsWith(defaultUserConfigPrefix)) {
                String   username = envName.replaceFirst(defaultUserConfigPrefix, "");
                String[] parts    = envValue.split(" ");

                if (parts.length < 2) {
                    throw new InvalidConfigException();
                }

                String       password = parts[0];
                List<String> groups   = Arrays.asList(Arrays.copyOfRange(parts, 1, parts.length));

                DefaultUser defaultUser = new DefaultUser();
                defaultUser.setUsername(username);
                defaultUser.setPassword(password);
                defaultUser.setGroups(groups);
                retList.add(defaultUser);
            }
        });
        return retList;
    }

    /**
     * Validates that the database contains all the {@link Group} names. If not, any missing name is added.
     */
    private void persistUserGroups() {
        long groups = (long) entityManager.createQuery("SELECT count(g.name) from Group g").getSingleResult();
        if (groups != Group.GROUPS.length) {
            for (String group : Group.GROUPS) {
                entityManager.persist(new Group(group));
            }
        }
    }


    private void createIfAbsent(DefaultUser defaultUser) {
        Optional<AuthenticatedUser> user = authenticationService.getUserFromPrincipal(defaultUser.getUsername());

        if (user.isEmpty()) {
            var newAuthUserData = new NewAuthUserData();
            newAuthUserData.setUserName(defaultUser.getUsername());
            newAuthUserData.setPassword(defaultUser.getPassword());
            newAuthUserData.setGroups(defaultUser.getGroups());
            var authUser = authenticationService.createUser(newAuthUserData);
        }
    }

    @Data
    @NoArgsConstructor
    private static class DefaultUser {

        private String username;
        private String password;
        private List<String> groups;
    }


}