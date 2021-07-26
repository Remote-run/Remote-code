package no.ntnu.remotecode.slave.control;


import io.jsonwebtoken.Claims;
import lombok.extern.java.Log;
import no.ntnu.remotecode.model.Project;
import no.ntnu.remotecode.model.Template;
import no.ntnu.remotecode.model.enums.ContainerStatus;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claim;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log
@Transactional
@RequestScoped
public class ProjectService {

    @Inject
    TemplateService templateService;

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    @Claim(Claims.SUBJECT)
    Instance<Optional<String>> jwtSubject;

    @Inject
    @ConfigProperty(name = "remote.code.shared.files.path", defaultValue = "/application_storage/shared_files")
    private String sharedDataDir;


    @Inject
    @Singleton
    ContainerStateManager containerStateManager;

    private static final String GET_USER_PROJECTS = "select pr from Project as pr where pr.containerOwnerId = :uid";


    private static final String FIND_PROJECT_BY_TEMPLATE_KEY = "SELECT pr from Project as pr where pr.containerTemplate.templateKey = :key";


    private File getContainerSaveDir() {
        return new File(sharedDataDir, "container_dirs");
    }

    private long getUserId() {
        return Long.parseLong(jwtSubject.get().get());
    }


    public Optional<Project> getProjectFromTemplateKey(UUID key) {

        TypedQuery<Project> query = entityManager.createQuery(FIND_PROJECT_BY_TEMPLATE_KEY, Project.class);
        query.setParameter("key", key);
        try {
            return Optional.of(query.getSingleResult());
        } catch (PersistenceException e) {
            return Optional.empty();
        }
    }

    public List<Project> getUserProjects() {
        long userId = getUserId();

        TypedQuery<Project> query = entityManager.createQuery(GET_USER_PROJECTS, Project.class);
        query.setParameter("uid", userId);
        return query.getResultList();
    }

    public boolean deleteProject(double projId) {
        Optional<Project> optionalProject = Optional.ofNullable(entityManager.find(Project.class, projId));

        if (optionalProject.isPresent()) {
            entityManager.remove(optionalProject.get());
            return true;
        } else {
            return false;
        }
    }

    public boolean changeProjectPass(double projId, String newPass) {

        Optional<Project> optionalProject = Optional.ofNullable(entityManager.find(Project.class, projId));
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.setAccessesKey(newPass);
            entityManager.persist(project);
            return true;
        } else {
            return false;
        }

    }


    public Optional<Project> initializeTemplate(UUID uuid) {
        Optional<Project> existingProject = getProjectFromTemplateKey(uuid);
        if (existingProject.isPresent()) {
            return existingProject;
        }

        Optional<Template> templateOptional = templateService.getTemplateFromKey(uuid);

        if (templateOptional.isPresent()) {
            Template template   = templateOptional.get();
            UUID     projectKey = UUID.randomUUID();

            File buildDir = new File(getContainerSaveDir(), projectKey.toString());
            buildDir.mkdirs();


            Project newProject = new Project();


            newProject.setContainerTemplate(template);
            newProject.setProjectKey(projectKey);
            newProject.setContainerName(projectKey.toString());
            newProject.setContainerOwnerId(getUserId());
            newProject.setContainerStatus(ContainerStatus.REQUESTED);
            newProject.setDataDirName(projectKey.toString());


            newProject.setAccessesKey(UUID.randomUUID().toString());

            entityManager.persist(newProject);

            containerStateManager.RequestNewState(projectKey, ContainerStatus.RUNNING);

            return Optional.of(newProject);

        } else {
            return Optional.empty();
        }
    }

}
