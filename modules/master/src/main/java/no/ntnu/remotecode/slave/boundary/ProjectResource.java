package no.ntnu.remotecode.slave.boundary;


import no.ntnu.remotecode.model.ContainerTask;
import no.ntnu.remotecode.model.Project;
import no.ntnu.remotecode.model.Template;
import no.ntnu.remotecode.model.enums.ContainerAction;
import no.ntnu.remotecode.model.enums.ContainerStatus;
import no.ntnu.remotecode.slave.kafka.MessageSender;
import no.ntnu.remotecode.slave.control.ProjectService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("projects")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
//@RolesAllowed(value = {Group.USER_GROUP_NAME})
public class ProjectResource {

    @Inject
    MessageSender mms;

    @GET
    @Path("/test")
    public Response test() {
        Template template = new Template();

        template.setCreatorId(1);
        template.setTemplateName("test-manual");
        template.setBuildDirName("test_tf_dir");
        template.setTemplateImageName("testi-imagename");
        template.setGitCloneRepo("https://github.com/Remote-run/remote-code-tf-test.git");

        Project project = new Project();

        project.setContainerTemplate(template);
        project.setContainerName("abc-test");
        project.setContainerOwnerId(2);
        project.setContainerStatus(ContainerStatus.REQUESTED);
        project.setDataDirName("test-dir-name");

        ContainerTask task = new ContainerTask();

        task.setProject(project);
        task.setAction(ContainerAction.START);
        task.setReceiverId(123);


        mms.submitMessage("test", "alifhuliaewh", "test test bop bip");
        return Response.ok().build();
    }


    @Inject
    ProjectService projectService;

    /**
     * Return a list of the logged in {@link no.woldseth.auth.model.AuthenticatedUser}'s {@link Project}'s.
     *
     * @return A list of the logged in user's project.
     */
    @GET
    @Valid
    public Response getCurrentProjects() {
        return Response.ok().build();
    }


    /**
     * Deletes the {@link Project} with the provided id if the logged in {@link no.woldseth.auth.model.AuthenticatedUser} owns it.
     *
     * @param projectId The id of the {@link Project} to delete;
     * @return 200 if ok.
     */
    @DELETE
    @Valid
    @Path("/{id}")
    public Response deleteProject(@PathParam("id") Integer projectId) {
        return Response.ok().build();
    }

    /**
     * Changes the project access password for the {@link Project} with the provided id if the requesting {@link no.woldseth.auth.model.AuthenticatedUser} owns it.
     *
     * @param projectId   The id of the project to change the password for
     * @param newPassword The new password to chang to.
     * @return 200 if ok
     */
    @PATCH
    @Valid
    @Path("/{id}")
    public Response changeProjectPass(@PathParam("id") Integer projectId, @NotBlank String newPassword) {
        return Response.ok().build();
    }


    /**
     * Initialize the a new {@link Project} from the provided template id.
     * If the {@link no.woldseth.auth.model.AuthenticatedUser} allredy have a project
     * made for this template that is returned
     *
     * @param templateId The id of the template to create a project from.
     * @return 200 if ok.
     */
    @GET
    @Valid
    @Path("/new/{id}")
    public Response initializeNewProject(@PathParam("id") String templateId) {
        return Response.ok().build();
    }
}
