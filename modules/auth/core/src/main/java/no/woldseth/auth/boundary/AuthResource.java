package no.woldseth.auth.boundary;


import no.woldseth.auth.model.AuthenticatedUser;
import no.woldseth.auth.model.DTO.NewAuthUserData;
import no.woldseth.auth.model.DTO.UserChangePasswordData;
import no.woldseth.auth.model.DTO.UsernamePasswordData;
import no.woldseth.auth.model.Group;
import no.woldseth.auth.control.AuthenticationService;


import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

/**
 * Implementes the REST HTTP API for the Auth-component of the Microservice
 * <p>
 * Uses {@link AuthenticationService} to process the requests.
 */
@Path("authentication")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {


    @Inject
    AuthenticationService authService;


    /**
     * Returns a Jwt token if the provided {@link UsernamePasswordData} yields a valid login,
     * Returns http 401 response if not.
     *
     * @param usernamePasswordData the {@link UsernamePasswordData} object.
     * @return http 200 with the Json web token in the {@link HttpHeaders#AUTHORIZATION} of the response.
     */
    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(
            @Valid UsernamePasswordData usernamePasswordData) {
        Optional<String> loginToken = authService.getToken(usernamePasswordData);

        return loginToken.map(s -> Response.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + s))
                         .orElse(Response.ok().status(Response.Status.UNAUTHORIZED)).build();

    }


    /**
     * Change the password for the current logged in {@link AuthenticatedUser} to the values spesified in the provided {@link UserChangePasswordData}
     *
     * @param changPasswordData the {@link UserChangePasswordData} object.
     * @return http 200 on sucsess if wrong old password http 403 is returned
     */
    @PUT
    @Path("changepass")
    @Valid
    public Response changePassword(
            @Valid UserChangePasswordData changPasswordData) {
        if (authService.changePassword(changPasswordData.getNewPassword(), changPasswordData.getOldPassword())) {
            return Response.ok().build();
        } else {
            return Response.ok().status(Response.Status.FORBIDDEN).build();
        }
    }


    /**
     * Adds a new {@link AuthenticatedUser} from the provided {@link NewAuthUserData}.
     * This endpoint is used by other services not Users.
     *
     * @param newAuthUserData the {@link NewAuthUserData} object.
     * @return a new auth user object if successful null if not
     */
    @POST
    @Path("newuser")
    @RolesAllowed(value = {Group.ADMIN_GROUP_NAME})
    public AuthenticatedUser newUser(NewAuthUserData newAuthUserData) {
        if (!authService.isPrincipalInUse(newAuthUserData.getUserName())) {
            return authService.createUser(newAuthUserData).orElse(null);
        }
        return null;
    }


    /**
     * Creates a new {@link AuthenticatedUser} in the User {@link Group}
     *
     * @param username The username for the new user
     * @param password The Password for the new user
     * @return the newly created authenticated user.
     */
    //TODO: This is horrible and shold be changed
    @POST
    @Path("newuser")
    @PermitAll
    public Response newUserUser(
            @QueryParam("username") String username, @QueryParam("password") String password) {
        NewAuthUserData newAuthUserData = new NewAuthUserData();
        newAuthUserData.setUserName(username);
        newAuthUserData.setPassword(password);
        newAuthUserData.setGroups(List.of(Group.USER_GROUP_NAME));

        Optional<AuthenticatedUser> newUser = Optional.empty();
        if (!authService.isPrincipalInUse(newAuthUserData.getUserName())) {
            newUser = authService.createUser(newAuthUserData);
        }


        if (newUser.isPresent()) {
            UsernamePasswordData usernamePasswordData = new UsernamePasswordData();
            usernamePasswordData.setPassword(password);
            usernamePasswordData.setUserName(username);

            Optional<String> loginToken = authService.getToken(usernamePasswordData);
            if (loginToken.isPresent()) {
                return loginToken.map(s -> Response.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + s))
                                 .orElse(Response.ok().status(Response.Status.UNAUTHORIZED)).build();
            }
        }

        return Response.ok().status(Response.Status.BAD_REQUEST).build();

    }
}
