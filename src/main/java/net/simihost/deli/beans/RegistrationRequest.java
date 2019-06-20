package net.simihost.deli.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.simihost.deli.entities.Gender;
import net.simihost.deli.entities.UserRegistration;
import net.simihost.deli.entities.account.UserDetails;
import net.simihost.deli.validation.EitherNotNull;
import net.simihost.deli.validation.FieldMatch;
import net.simihost.deli.validation.ValidEmail;
import net.simihost.deli.validation.ValidMobileNo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Past;
import java.util.Date;

/**
 *
 * Created by Rashed on 23/05/2019
 *
 */
@FieldMatch(first = "password", second = "confirmedPassword", message = "password field must match confirmedPassword.")
@EitherNotNull(first = "email", second = "mobileNo")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationRequest {

    @JsonIgnore
    private Long registrationId;
    @NotBlank
    private String fullName;
    @ValidMobileNo
    private String mobileNo;
    @ValidEmail
    private String email;
    @JsonIgnore
    @NotBlank
    @Length(min = 6, max = 254)
    private String password;
    @JsonIgnore
    private String confirmedPassword;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Past
    private Date birthDate;
    private Gender gender;
    private String address;

    @JsonProperty
    public Long getRegistrationId() {
        return registrationId;
    }

    @JsonIgnore
    public void setRegistrationId(Long registrationId) {
        this.registrationId = registrationId;
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

    @JsonIgnore
    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    @JsonProperty
    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

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

    public UserRegistration createRegistrationEntity() {
        UserRegistration registration = new UserRegistration();

        UserDetails userDetails = new UserDetails();
        userDetails.setFullName(getFullName());
        userDetails.setMobileNo(getMobileNo());
        userDetails.setEmail(getEmail());
        userDetails.setPassword(getPassword());
        userDetails.setConfirmedPassword(getConfirmedPassword());
        userDetails.setBirthDate(getBirthDate());
        userDetails.setGender(getGender());
        userDetails.setAddress(getAddress());

        registration.setUserDetails(userDetails);
        return registration;
    }

    public static RegistrationRequest convertToDTO(UserRegistration entity){
        if(entity == null)
            return null;

        RegistrationRequest dto = new RegistrationRequest();
        dto.setRegistrationId(entity.getId());
        if(entity.getUserDetails() != null) {
            dto.setFullName(entity.getUserDetails().getFullName());
            dto.setEmail(entity.getUserDetails().getEmail());
            dto.setMobileNo(entity.getUserDetails().getMobileNo());
            dto.setGender(entity.getUserDetails().getGender());
            dto.setBirthDate(entity.getUserDetails().getBirthDate());
            dto.setAddress(entity.getUserDetails().getAddress());
        }

        return dto;
    }

    @Override
    public String toString() {
        return "RegistrationRequest{" +
                "registrationId=" + registrationId +
                ", fullName='" + fullName + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", email='" + email + '\'' +
                ", password='password'" +
                ", confirmedPassword='confirmedPassword'" +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                '}';
    }
}
