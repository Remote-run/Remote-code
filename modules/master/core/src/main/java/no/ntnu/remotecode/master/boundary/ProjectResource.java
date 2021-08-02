package no.ntnu.remotecode.master.boundary;


import no.ntnu.remotecode.master.model.ContainerTask;
import no.ntnu.remotecode.master.model.Project;
import no.ntnu.remotecode.master.model.Template;
import no.ntnu.remotecode.master.model.enums.ContainerAction;
import no.ntnu.remotecode.master.model.enums.ContainerStatus;
import no.ntnu.remotecode.master.kafka.MessageSender;
import no.ntnu.remotecode.master.control.ProjectService;
import no.woldseth.auth.model.Group;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Path("projects")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
@RolesAllowed(value = {Group.USER_GROUP_NAME})
public class ProjectResource {

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
        List<Project> projects = projectService.getUserProjects();
        return Response.ok(projects).build();
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
    public Response deleteProject(@PathParam("id") UUID projectId) {
        boolean suc = projectService.deleteProject(projectId);
        if (suc) {
            return Response.ok().build();
        } else {

            return Response.ok().status(Response.Status.NOT_FOUND).build();
        }
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
    public Response changeProjectPass(@PathParam("id") UUID projectId, @NotBlank String newPassword) {
        boolean suc = projectService.changeProjectPass(projectId, newPassword);
        if (suc) {
            return Response.ok().build();
        } else {
            return Response.ok().status(Response.Status.NOT_FOUND).build();

        }
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
    public Response initializeNewProject(@PathParam("id") UUID templateId) {
        Optional<Project> project = projectService.initializeTemplate(templateId);

        if (project.isPresent()) {
            return Response.ok(project.get()).build();
        } else {
            return Response.ok().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
