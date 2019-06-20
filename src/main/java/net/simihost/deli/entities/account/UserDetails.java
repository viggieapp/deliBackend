package net.simihost.deli.entities.account;

import net.simihost.deli.entities.Gender;
import net.simihost.deli.entities.Gender;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 *
 * Created by Rashed on  15/04/2019
 *
 */
@Embeddable
public class UserDetails {

    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "mobile_no", unique = true, nullable = true)
    private String mobileNo;
    @Email
    @Column(unique = true, nullable = true)
    private String email;
    @NotEmpty
    @Column(nullable = false)
    private String password;
    @Transient
    private String confirmedPassword;
    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date")
    private Date birthDate;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String address;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetails that = (UserDetails) o;
        return Objects.equals(fullName, that.fullName) &&
                Objects.equals(mobileNo, that.mobileNo) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(confirmedPassword, that.confirmedPassword) &&
                Objects.equals(birthDate, that.birthDate) &&
                gender == that.gender &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, mobileNo, email, password, confirmedPassword, birthDate, gender, address);
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "fullName='" + fullName + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmedPassword='" + confirmedPassword + '\'' +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                '}';
    }
}
