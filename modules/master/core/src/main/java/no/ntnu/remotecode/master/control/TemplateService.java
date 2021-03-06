package no.ntnu.remotecode.master.control;


import io.jsonwebtoken.Claims;
import lombok.extern.java.Log;
import no.ntnu.remotecode.master.docker.DockerFileBuilder;
import no.ntnu.remotecode.master.model.DTO.web.NewTemplateDTO;
import no.ntnu.remotecode.master.model.Template;
import no.ntnu.remotecode.master.model.enums.TemplateStatus;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private String baseImageName = "remote-code-base";

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

        List<Template> res = query.getResultList();
        res.removeIf(template -> template.getTemplateStatus().equals(TemplateStatus.DELETED));

        return res;
    }

    public boolean deleteTemplate(UUID templateId) {

        Optional<Template> templateOptional = getTemplateFromKey(templateId);
        if (templateOptional.isPresent()) {
            Template template = templateOptional.get();
            template.setTemplateStatus(TemplateStatus.DELETED);
            entityManager.persist(template);

            return true;
        } else {
            return false;
        }


    }

    public boolean createNewTemplate(NewTemplateDTO newTemplateDTO) throws IOException {


        UUID templateKey = UUID.randomUUID();


        File    buildDir = new File(getTemplateSaveDir(), templateKey.toString());
        boolean suc      = buildDir.mkdirs();

        if (!suc) {
            throw new IOException("Unable to create template dir");
        }

        Template newTemplate = new Template();

        newTemplate.setTemplateKey(templateKey);

        newTemplate.setTemplateName(newTemplateDTO.getTemplateName());
        newTemplate.setGitCloneRepo(newTemplateDTO.getGithubLink());
        newTemplate.setCreatorId(getUserId());
        newTemplate.setBuildDirName(templateKey.toString());
        newTemplate.setTemplateStatus(TemplateStatus.ACTIVE);


        DockerFileBuilder builder = new DockerFileBuilder(baseImageName);

        builder.addLines(newTemplateDTO.getDockerBuildSteps());

        builder.writeToFile(new File(buildDir, "Dockerfile"));

        newTemplate.setTemplateImageName(templateKey.toString());

        entityManager.persist(newTemplate);

        return true;
    }

}
