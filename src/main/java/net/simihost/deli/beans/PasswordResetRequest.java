package net.simihost.deli.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Ruba Hamad on 23/05/2019
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PasswordResetRequest {

    @NotBlank
    private String userIdentifier;
    @JsonIgnore
    private String newPassword;

    public String getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "ResetPasswordRequest{" +
                "userIdentifier='" + userIdentifier + '\'' +
                '}';
    }
}
