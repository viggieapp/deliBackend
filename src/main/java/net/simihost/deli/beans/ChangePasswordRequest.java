package net.simihost.deli.beans;

import net.simihost.deli.validation.FieldMatch;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Ruba Hamad on 23/05/2019
 *
 */
@FieldMatch(first = "password", second = "confirmedPassword", message = "password field must match confirmedPassword.")
public class ChangePasswordRequest {

    @NotBlank
    private String oldPassword;
    @NotBlank
    @Length(min = 6,max = 254)
    private String password;
    private String confirmedPassword;
    private Long userId;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ChangePasswordRequest{" +
                "oldPassword='" + oldPassword + '\'' +
                ", password='" + password + '\'' +
                ", confirmedPassword='" + confirmedPassword + '\'' +
                ", userId=" + userId +
                '}';
    }
}
