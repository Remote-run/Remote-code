package no.ntnu.remotecode.slave.control;


import io.jsonwebtoken.Claims;
import lombok.extern.java.Log;
import no.ntnu.remotecode.model.Project;
import org.eclipse.microprofile.jwt.Claim;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log
@Transactional
@RequestScoped
public class ProjectService {

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    @Claim(Claims.SUBJECT)
    Instance<Optional<String>> jwtSubject;

    private static final String GET_USER_PROJECTS = "select pr from Project as pr where pr.containerOwnerId = :uid";

    private long getUserId() {
        return Long.parseLong(jwtSubject.get().get());
    }

    public List<Project> getUserProjects() {
        long userId = getUserId();

        TypedQuery<Project> query = entityManager.createQuery(GET_USER_PROJECTS, Project.class);
        query.setParameter("uid", userId);
        return query.getResultList();
    }

    public void deleteProject(double projId) {
        Optional.ofNullable(entityManager.find(Project.class, projId))
                .ifPresent(project -> entityManager.remove(project));
    }

    public boolean changeProjectPass(double projId, String newPass) {

        Optional<Project> optionalProject = Optional.ofNullable(entityManager.find(Project.class, projId));
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.setAccessesKey(newPass);
            entityManager.persist(project);
            return true
        } else {
            return false;
        }

    }

}
