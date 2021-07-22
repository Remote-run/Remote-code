package no.ntnu.remotecode.slave.boundary;


import no.ntnu.remotecode.model.DTO.web.NewTemplateDTO;
import no.ntnu.remotecode.model.Template;
import no.woldseth.auth.model.Group;


import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("templates")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(value = {Group.ADMIN_GROUP_NAME})
public class TemplateResource {


    /**
     * Returns a list of the logged in {@link no.woldseth.auth.model.AuthenticatedUser} users {@link Template}.
     *
     * @return A list of the logged in users templates.
     */
    @GET
    @Valid
    public Response getCurrentTemplates() {
        return Response.ok().build();
    }


    /**
     * Creates a new {@link Template} from the provided {@link NewTemplateDTO};
     *
     * @param newTemplate A dto containg the info requiered to create a new {@link Template}.
     *
     * @return 200 if ok
     */
    @POST
    @Valid
    @Path("new")
    public Response addNewTemplate(NewTemplateDTO newTemplate) {
        return Response.ok().build();
    }

}
