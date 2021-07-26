package no.ntnu.remotecode.master.boundary;


import no.ntnu.remotecode.model.DTO.web.NewTemplateDTO;
import no.ntnu.remotecode.model.Template;
import no.ntnu.remotecode.master.control.TemplateService;
import no.woldseth.auth.model.Group;


import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;


@Path("templates")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(value = {Group.ADMIN_GROUP_NAME})
public class TemplateResource {


    @Inject
    TemplateService templateService;


    /**
     * Returns a list of the logged in {@link no.woldseth.auth.model.AuthenticatedUser} users {@link Template}.
     *
     * @return A list of the logged in users templates.
     */
    @GET
    @Valid
    public Response getCurrentTemplates() {
        List<Template> templates = templateService.getUserTemplates();
        return Response.ok(templates).build();
    }


    /**
     * Creates a new {@link Template} from the provided {@link NewTemplateDTO};
     *
     * @param newTemplate A dto containg the info requiered to create a new {@link Template}.
     * @return 200 if ok
     */
    @POST
    @Valid
    @Path("new")
    public Response addNewTemplate(NewTemplateDTO newTemplate) {
        Response.ResponseBuilder response;
        try {
            boolean suc = templateService.createNewTemplate(newTemplate);
            if (suc) {
                response = Response.ok();
            } else {
                response = Response.ok().status(Response.Status.INTERNAL_SERVER_ERROR);
            }
        } catch (IOException e) {
            response = Response.ok().status(Response.Status.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return response.build();
    }

}
