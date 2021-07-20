package no.woldseth.auth;


import no.woldseth.auth.model.Group;

import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;


@DatabaseIdentityStoreDefinition(dataSourceLookup = "jdbc/auth-db", callerQuery = "select password from auth_users as us where cast(us.id as text)  = ?", groupsQuery = "select groups_name from user_groups as ug, auth_users as us where cast(us.id as text) = ? and us.id = ug.user_id", priority = 80)
// Roles allowed for authentication
@DeclareRoles({Group.ADMIN_GROUP_NAME, Group.USER_GROUP_NAME})
@ApplicationScoped
@ApplicationPath("/")
public class AuthApplication extends Application {

}
