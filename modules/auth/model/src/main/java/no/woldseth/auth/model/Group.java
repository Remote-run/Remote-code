package no.woldseth.auth.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Represents the differen groops used for authentication in the application.
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "group_names")
public class Group implements Serializable {

    /**
     * The base group in the application containing all users.
     * Used for endpoints where the requirement is simply being logged in.
     */
    public static final String USER_GROUP_NAME = "user";

    /**
     * The admin groop is responsible for administrating the other users.
     * The admin has free reign to change modify, delete or add any data in the system.
     */
    public static final String ADMIN_GROUP_NAME = "admin";


    public static final String[] GROUPS = {USER_GROUP_NAME, ADMIN_GROUP_NAME};

    /**
     * The group name.
     */
    @Id
    String name;

    public Group(String name) {
        this.name = name;
    }

    public static boolean isValidGroupName(String name) {
        return Arrays.asList(GROUPS).contains(name);
    }

}