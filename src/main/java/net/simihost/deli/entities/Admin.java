package net.simihost.deli.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import javax.persistence.*;
import java.util.*;

/**
 *
 * @author Ruba Hamad on 15/04/2019
 *
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="admins")
public class Admin extends BaseEntity {

    private static final long serialVersionUID = 3344585642081549555L;

    @JsonUnwrapped
    @Embedded
    private AdminDetails adminDetails;
    @Column(nullable = false)
    private boolean enabled;
    @Column(nullable = false)
    private boolean locked;
    @Column(name = "has_logged_out")
    private boolean hasLoggedOut;
    @Column(name = "login_try_count")
    private int loginTryCount;
    @Column(name = "disabled_date")
    private Date disabledDate;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "admin_roles", joinColumns = @JoinColumn(name = "admin_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Admin() {}

    public Admin(AdminDetails details) {
        this.adminDetails = new AdminDetails();
        this.adminDetails.setFullName(details.getFullName());
        this.adminDetails.setEmail(details.getEmail());
        this.adminDetails.setPassword(details.getPassword());
        this.adminDetails.setConfirmedPassword(details.getConfirmedPassword());
        this.adminDetails.setBirthDate(details.getBirthDate());
        this.adminDetails.setGender(details.getGender());
        this.adminDetails.setAddress(details.getAddress());
    }

    public AdminDetails getAdminDetails() {
        return adminDetails;
    }

    public void setAdminDetails(AdminDetails adminDetails) {
        this.adminDetails = adminDetails;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isHasLoggedOut() {
        return hasLoggedOut;
    }

    public void setHasLoggedOut(boolean hasLoggedOut) {
        this.hasLoggedOut = hasLoggedOut;
    }

    public int getLoginTryCount() {
        return loginTryCount;
    }

    public void setLoginTryCount(int loginTryCount) {
        this.loginTryCount = loginTryCount;
    }

    public Date getDisabledDate() {
        return disabledDate;
    }

    public void setDisabledDate(Date disabledDate) {
        this.disabledDate = disabledDate;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRoles(Role... roles) {
        this.roles.addAll(Arrays.asList(roles));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return enabled == admin.enabled &&
                locked == admin.locked &&
                hasLoggedOut == admin.hasLoggedOut &&
                loginTryCount == admin.loginTryCount &&
                Objects.equals(adminDetails, admin.adminDetails) &&
                Objects.equals(disabledDate, admin.disabledDate) &&
                Objects.equals(roles, admin.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adminDetails);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminDetails=" + adminDetails.toString() +
                ", enabled=" + enabled +
                ", locked=" + locked +
                ", hasLoggedOut=" + hasLoggedOut +
                ", loginTryCount=" + loginTryCount +
                ", disabledDate=" + disabledDate +
                ", roles=" + roles +
                '}';
    }
}
