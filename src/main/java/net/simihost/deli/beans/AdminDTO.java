package net.simihost.deli.beans;

import com.fasterxml.jackson.annotation.*;
import net.simihost.deli.entities.*;

import java.util.*;

/**
 *
 * @author Ruba Hamad on 17/04/2019
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true, value = {"version"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AdminDTO {

    private Long id;
    private String fullName;
    private String email;
    @JsonIgnore
    private String password;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date birthDate;
    private Gender gender;
    private String address;
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

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    public static AdminDTO convertToDTO(Admin entity) {
        if(entity == null)
            return null;

        AdminDTO dto = new AdminDTO();
        dto.setId(entity.getId());

        if(entity.getAdminDetails() != null) {
            dto.setFullName(entity.getAdminDetails().getFullName());
            dto.setEmail(entity.getAdminDetails().getEmail());
            dto.setBirthDate(entity.getAdminDetails().getBirthDate());
            dto.setGender(entity.getAdminDetails().getGender());
            dto.setAddress(entity.getAdminDetails().getAddress());
        }

        // Roles
        Set<RoleDTO> roles = new HashSet<>();
        for(Role role: entity.getRoles()) {
            roles.add(RoleDTO.convertToDTO(role));
        }
        dto.setRoles(roles);

        return dto;
    }

    public static List<AdminDTO> convertToDTOs(List<Admin> entities) {
        List<AdminDTO> dtos = new ArrayList<>();
        for(Admin entity : entities)
            dtos.add(AdminDTO.convertToDTO(entity));
        return dtos;
    }

    @Override
    public String toString() {
        return "AdminDTO{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                ", roles=" + roles +
                '}';
    }
}
