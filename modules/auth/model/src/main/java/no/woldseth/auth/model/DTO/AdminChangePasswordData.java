package no.woldseth.auth.model.DTO;

import lombok.Data;
import no.woldseth.auth.model.AuthenticatedUser;

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
