package no.ntnu.remotecode.slave.control;


import io.jsonwebtoken.Claims;
import lombok.extern.java.Log;
import no.ntnu.remotecode.slave.docker.DockerFileBuilder;
import no.ntnu.remotecode.model.DTO.web.NewTemplateDTO;
import no.ntnu.remotecode.model.Template;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claim;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log
@Transactional
@RequestScoped
public class TemplateService {

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    @Claim(Claims.SUBJECT)
    Instance<Optional<String>> jwtSubject;

    @Inject
    @ConfigProperty(name = "remote.code.shared.files.path", defaultValue = "/application_storage/shared_files")
    private String sharedDataDir;

    //TODO: FIX/FILL
    private String baseImageName = " ";

    private static final String GET_USER_TEMPLATES = "select tm from Template as tm where tm.creatorId = :uid";
    private static final String GET_TEMPLATE_FROM_STRING = "select tm from Template as tm where tm.templateKey = :key";

    private File getTemplateSaveDir() {
        return new File(sharedDataDir, "templates");
    }

    private long getUserId() {
        return Long.parseLong(jwtSubject.get().get());
    }

    public Optional<Template> getTemplateFromKey(UUID key) {

        TypedQuery<Template> query = entityManager.createQuery(GET_TEMPLATE_FROM_STRING, Template.class);
        query.setParameter("key", key);
        try {
            return Optional.of(query.getSingleResult());
        } catch (PersistenceException e) {
            return Optional.empty();
        }
    }

    public List<Template> getUserTemplates() {
        long userId = getUserId();

        TypedQuery<Template> query = entityManager.createQuery(GET_USER_TEMPLATES, Template.class);
        query.setParameter("uid", userId);
        return query.getResultList();
    }

    public boolean createNewTemplate(NewTemplateDTO newTemplateDTO) throws IOException {


        UUID templateKey = UUID.randomUUID();


        File buildDir = new File(getTemplateSaveDir(), templateKey.toString());
        buildDir.mkdirs();

        Template newTemplate = new Template();

        newTemplate.setTemplateKey(templateKey);

        newTemplate.setTemplateName(newTemplateDTO.getTemplateName());
        newTemplate.setGitCloneRepo(newTemplateDTO.getGithubLink());
        newTemplate.setCreatorId(getUserId());
        newTemplate.setBuildDirName(templateKey.toString());


        DockerFileBuilder builder = new DockerFileBuilder(baseImageName);

        builder.addLines(newTemplateDTO.getDockerBuildSteps());

        builder.writeToFile(new File(buildDir, "Dockerfile"));

        newTemplate.setTemplateImageName(templateKey.toString());

        entityManager.persist(newTemplate);

        return true;
    }

}
