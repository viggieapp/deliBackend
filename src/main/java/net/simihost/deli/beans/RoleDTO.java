package net.simihost.deli.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import net.simihost.deli.entities.Role;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Ruba Hamad on 17/04/2019
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDTO {

    private Long id;
    private String roleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public static RoleDTO convertToDTO(Role entity) {
        if(entity == null)
            return null;

        RoleDTO dto = new RoleDTO();
        dto.setId(entity.getId());
        dto.setRoleName(entity.getRoleName());

        return dto;
    }

    public static Set<RoleDTO> convertToDTOs(Set<Role> entities) {
        Set<RoleDTO> dtos = new HashSet<>();
        for(Role entity : entities)
            dtos.add(RoleDTO.convertToDTO(entity));
        return dtos;
    }

}
