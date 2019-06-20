package net.simihost.deli.entities.account;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import net.simihost.deli.entities.BaseEntity;
import net.simihost.deli.entities.Role;
import net.simihost.deli.entities.BaseEntity;
import net.simihost.deli.entities.Role;

import javax.persistence.*;
import java.util.*;

/**
 *
 * Created by Rashed on  15/04/2019
 *
 */
@MappedSuperclass
public abstract class BaseUser extends BaseEntity {

    private static final long serialVersionUID = -9011796470853569960L;

    @JsonUnwrapped
    @Embedded
    private UserDetails userDetails;
    @Column(name = "registration_id", nullable = false, unique = true)
    private Long registrationId;
    @Column(name = "push_notification_id")
    private String pushNotificationId;
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
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public BaseUser() {
        setEnabled(true);
        setLocked(false);
        setHasLoggedOut(true);
        setLoginTryCount(0);
    }

    public BaseUser(UserDetails details) {
        this();
        this.userDetails = new UserDetails();
        this.userDetails.setFullName(details.getFullName());
        this.userDetails.setEmail(details.getEmail());
        this.userDetails.setMobileNo(details.getMobileNo());
        this.userDetails.setPassword(details.getPassword());
        this.userDetails.setConfirmedPassword(details.getConfirmedPassword());
        this.userDetails.setBirthDate(details.getBirthDate());
        this.userDetails.setGender(details.getGender());
        this.userDetails.setAddress(details.getAddress());
    }

    public String getUserIdentifier() {
        String userIdentifier = null;
        userIdentifier = userDetails.getMobileNo() != null ? userDetails.getMobileNo() : userDetails.getEmail();
        return userIdentifier;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public Long getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Long registrationId) {
        this.registrationId = registrationId;
    }

    public String getPushNotificationId() {
        return pushNotificationId;
    }

    public void setPushNotificationId(String pushNotificationId) {
        this.pushNotificationId = pushNotificationId;
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
        if (!(o instanceof BaseUser)) return false;
        BaseUser that = (BaseUser) o;
        if(userDetails == null) {
            if(that.userDetails != null)
                return false;
            return false;
        } else {
            if(that.userDetails == null) return false;
            return getUserIdentifier().equals(that.getUserIdentifier());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(userDetails);
    }

    @Override
    public String toString() {
        return "BaseUser{" +
                "userDetails=" + userDetails.toString() +
                ", registrationId=" + registrationId +
                ", pushNotificationId='" + pushNotificationId + '\'' +
                ", enabled=" + enabled +
                ", locked=" + locked +
                ", hasLoggedOut=" + hasLoggedOut +
                ", loginTryCount=" + loginTryCount +
                ", disabledDate=" + disabledDate +
                ", roles=" + roles +
                '}';
    }
}
