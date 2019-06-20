package net.simihost.deli.beans.account;

import com.fasterxml.jackson.annotation.*;
import net.simihost.deli.beans.RoleDTO;
import net.simihost.deli.entities.Gender;
import net.simihost.deli.entities.account.User;

import java.util.*;

/**
 *
 * Created by Rashed on  17/04/2019
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true, value = {"version"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDTO {

    private Long id;
    private String fullName;
    private String mobileNo;
    private String email;
    @JsonIgnore
    private String password;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;
    private Gender gender;
    private String address;
    private Long registrationId;
    private String pushNotificationId;
    private boolean enabled;
    private boolean locked;
    private boolean hasLoggedOut;
    private int loginTryCount;
    private Date disabledDate;
    private Set<RoleDTO> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    public static UserDTO convertToDTO(User entity) {
        if(entity == null)
            return null;

        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());

        if(entity.getUserDetails() != null) {
            dto.setFullName(entity.getUserDetails().getFullName());
            dto.setEmail(entity.getUserDetails().getEmail());
            dto.setMobileNo(entity.getUserDetails().getMobileNo());
            dto.setBirthDate(entity.getUserDetails().getBirthDate());
            dto.setGender(entity.getUserDetails().getGender());
            dto.setAddress(entity.getUserDetails().getAddress());
        }

        dto.setRegistrationId(entity.getRegistrationId());
        dto.setPushNotificationId(entity.getPushNotificationId());
        dto.setEnabled(entity.isEnabled());
        dto.setLocked(entity.isLocked());
        dto.setHasLoggedOut(entity.isHasLoggedOut());
        dto.setLoginTryCount(entity.getLoginTryCount());
        dto.setDisabledDate(entity.getDisabledDate());

        // Roles
        dto.setRoles(RoleDTO.convertToDTOs(entity.getRoles()));

        return dto;
    }

    public static List<UserDTO> convertToDTOs(List<User> entities) {
        List<UserDTO> dtos = new ArrayList<>();
        for(User entity : entities)
            dtos.add(UserDTO.convertToDTO(entity));
        return dtos;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", email='" + email + '\'' +
                ", password='password'" +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                ", locked=" + locked +
                ", roles=" + roles +
                '}';
    }
}
