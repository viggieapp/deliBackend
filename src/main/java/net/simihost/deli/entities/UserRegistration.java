package net.simihost.deli.entities;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import net.simihost.deli.entities.account.UserDetails;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Ruba Hamad on 23/05/2019
 *
 */
@Entity
@Table(name="registration")
@AttributeOverrides({
        @AttributeOverride(name = "mobileNo", column = @Column(name = "mobile_no", unique = false, nullable = true)),
        @AttributeOverride(name = "email", column = @Column(unique = false, nullable = true))
})
public class UserRegistration extends BaseEntity {

    private static final long serialVersionUID = 4361094317349480940L;

    @Valid
    @JsonUnwrapped
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "mobileNo",column = @Column(name = "mobile_no", unique = false, nullable = true)),
            @AttributeOverride(name = "email", column = @Column(unique = false, nullable = true))
    })
    private UserDetails userDetails;
    @Column(nullable = false)
    private boolean enabled;
    @Column(name = "activation_date")
    private Date activationDate;

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegistration that = (UserRegistration) o;
        return enabled == that.enabled &&
                Objects.equals(userDetails, that.userDetails) &&
                Objects.equals(activationDate, that.activationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userDetails, enabled, activationDate);
    }

    @Override
    public String toString() {
        return "UserRegistration{" +
                "userDetails=" + userDetails +
                ", enabled=" + enabled +
                ", activationDate=" + activationDate +
                '}';
    }
}
