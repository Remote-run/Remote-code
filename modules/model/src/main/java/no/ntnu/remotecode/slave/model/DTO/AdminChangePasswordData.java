package no.ntnu.remotecode.slave.model.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * DTO used to send admin change password data.
 * Used to facilitate Http POST payload usage for increased security.
 */
@Data
public class AdminChangePasswordData {

    /**
     * The id for the user to change the password for
     */
    @NotNull
    private Long userId;

    /**
     * The new password for the {@link AuthenticatedUser} with the {@link AdminChangePasswordData#userId}
     */
    @NotNull
    private String newPassword;
}
