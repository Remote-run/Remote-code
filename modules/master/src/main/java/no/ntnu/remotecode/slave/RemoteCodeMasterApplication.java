package no.ntnu.remotecode.slave;


import no.woldseth.auth.model.Group;

import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


@DeclareRoles({Group.USER_GROUP_NAME, Group.ADMIN_GROUP_NAME})
@ApplicationScoped
@ApplicationPath("/")
public class RemoteCodeMasterApplication extends Application {

}
