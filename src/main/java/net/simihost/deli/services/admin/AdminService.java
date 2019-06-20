package net.simihost.deli.services.admin;

import net.simihost.deli.beans.AdminDTO;
import net.simihost.deli.db.AdminRepository;
import net.simihost.deli.db.RoleRepository;
import net.simihost.deli.entities.Admin;
import net.simihost.deli.entities.AdminDetails;
import net.simihost.deli.entities.Role;
import net.simihost.deli.exceptions.AdminNotFoundException;
import net.simihost.deli.beans.AdminDTO;
import net.simihost.deli.db.AdminRepository;
import net.simihost.deli.db.RoleRepository;
import net.simihost.deli.entities.Admin;
import net.simihost.deli.entities.AdminDetails;
import net.simihost.deli.entities.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * Created by Rashed on  29/04/2019
 *
 */
@Service
@Transactional
public class AdminService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<AdminDTO> getAdmins() {
        return AdminDTO.convertToDTOs(findAdmins());

    }

    public AdminDTO getAdmin(Long adminId) {
        return AdminDTO.convertToDTO(findAdmin(adminId));
    }

    public AdminDTO addAdmin(AdminDTO adminDTO) {
        Admin admin = mapToNewAdmin(adminDTO);
        adminRepository.save(admin);
        return AdminDTO.convertToDTO(admin);
    }

    public AdminDTO updateAdmin(Long adminId, AdminDTO adminDTO) {
        Admin admin = findAdmin(adminId);
        return updateAdmin(admin, adminDTO);
    }

    public void deleteAdmin(Long adminId) {
        Admin admin = findAdmin(adminId);
        admin.setStatus("Inactive");
        adminRepository.save(admin);
    }

    private List<Admin> findAdmins() {
        return adminRepository.findByStatus("Active");
    }

    private Admin findAdmin(Long adminId) {
        Admin admin = adminRepository.findOneByIdAndStatus(adminId, "Active");
        if (admin == null)
            throw new AdminNotFoundException();

        return admin;
    }

    private AdminDTO updateAdmin(Admin admin, AdminDTO request) {
        if (request.getFullName() != null)
            admin.getAdminDetails().setFullName(request.getFullName());
        if(request.getEmail() != null)
            admin.getAdminDetails().setEmail(request.getEmail());
        if(request.getPassword() != null)
            admin.getAdminDetails().setPassword(passwordEncoder.encode(request.getPassword()));
        if (request.getBirthDate() != null)
            admin.getAdminDetails().setBirthDate(request.getBirthDate());
        if (request.getGender() != null)
            admin.getAdminDetails().setGender(request.getGender());
        if (request.getAddress() != null)
            admin.getAdminDetails().setAddress(request.getAddress());

        adminRepository.save(admin);
        return AdminDTO.convertToDTO(admin);
    }

    private Admin mapToNewAdmin(AdminDTO adminDTO) {
        AdminDetails adminDetails = new AdminDetails();
        adminDetails.setFullName(adminDTO.getFullName());
        adminDetails.setEmail(adminDTO.getEmail());
        adminDetails.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        adminDetails.setBirthDate(adminDTO.getBirthDate());
        adminDetails.setGender(adminDTO.getGender());
        adminDetails.setAddress(adminDTO.getAddress());

        Admin admin = new Admin(adminDetails);
        admin.setEnabled(true);
        admin.setLocked(false);
        admin.setLoginTryCount(0);
        admin.setStatus("Active");
        //Default admin role
        Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN");
        admin.addRoles(adminRole);

        return admin;
    }
}
