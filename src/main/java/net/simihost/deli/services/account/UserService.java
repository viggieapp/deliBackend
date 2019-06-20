package net.simihost.deli.services.account;

import net.simihost.deli.beans.ChangePasswordRequest;
import net.simihost.deli.beans.RoleDTO;
import net.simihost.deli.beans.account.UserDTO;
import net.simihost.deli.config.AppSetting;
import net.simihost.deli.db.RoleRepository;
import net.simihost.deli.db.UserRepository;
import net.simihost.deli.entities.Role;
import net.simihost.deli.entities.account.User;
import net.simihost.deli.entities.account.UserDetails;
import net.simihost.deli.exceptions.AuthenticationException;
import net.simihost.deli.exceptions.PasswordException;
import net.simihost.deli.exceptions.UserNotFoundException;
import org.apache.camel.Exchange;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Created by Rashed on  21/04/2019
 *
 */
@Service
public class UserService {

    @Autowired
    private AppSetting appSetting;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<UserDTO> getUsers() {
        return UserDTO.convertToDTOs(findUsers());

    }

    public UserDTO getUser(Long userId) {
        return UserDTO.convertToDTO(findUser(userId));
    }

    public UserDTO addUser(UserDTO userDTO) {
        userDTO.setPassword(RandomStringUtils.randomAlphanumeric(appSetting.getPasswordLength()));
        User user = mapToNewUser(userDTO);
        userRepository.save(user);
        //TODO send password to user.
        return UserDTO.convertToDTO(user);
    }

    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User user = findUser(userId);
        return updateUser(user, userDTO);
    }

    public void deleteUser(Long userId) {
        User user = findUser(userId);
        user.setStatus("Inactive");
        userRepository.save(user);
    }

    public void changePassword(ChangePasswordRequest request, Exchange exchange) {
        User user = findUser(request.getUserId());
        changePassword(user, request);
        exchange.getIn().setBody(null);
    }

    private List<User> findUsers() {
        return userRepository.findByStatus("Active");
    }

    public User findUser(Long userId) {
        User user = userRepository.findOneByIdAndStatus(userId, "Active");
        if (user == null)
            throw new UserNotFoundException();

        return user;
    }

    private User mapToNewUser(UserDTO userDTO) {
        UserDetails userDetails = new UserDetails();
        userDetails.setFullName(userDTO.getFullName());
        userDetails.setMobileNo(userDTO.getMobileNo());
        userDetails.setEmail(userDTO.getEmail());
        userDetails.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDetails.setBirthDate(userDTO.getBirthDate());
        userDetails.setGender(userDTO.getGender());
        userDetails.setAddress(userDTO.getAddress());

        User user = new User(userDetails);
        user.setStatus("Active");
        //Default user role
        Role userRole = roleRepository.findByRoleName("ROLE_USER");
        user.addRoles(userRole);

        return user;
    }

    private UserDTO updateUser(User user, UserDTO request) {
        if (request.getFullName() != null)
            user.getUserDetails().setFullName(request.getFullName());
        if(request.getMobileNo() != null)
            user.getUserDetails().setMobileNo(request.getMobileNo());
        if(request.getEmail() != null)
            user.getUserDetails().setEmail(request.getEmail());
        if (request.getBirthDate() != null)
            user.getUserDetails().setBirthDate(request.getBirthDate());
        if (request.getGender() != null)
            user.getUserDetails().setGender(request.getGender());
        if (request.getAddress() != null)
            user.getUserDetails().setAddress(request.getAddress());
        if(request.getRoles() != null) {
            for(RoleDTO roleDTO : request.getRoles()){
                Role role = roleRepository.findByRoleName(roleDTO.getRoleName());
                if(role != null) user.addRoles(role);
            }
        }
        user.setLocked(request.isLocked());

        userRepository.save(user);
        return UserDTO.convertToDTO(user);
    }

    private void changePassword(User user, ChangePasswordRequest request) {
        if (!passwordEncoder.matches(request.getOldPassword(), user.getUserDetails().getPassword()))
            throw new AuthenticationException();

        if (request.getPassword().equals(request.getConfirmedPassword()))
            user.getUserDetails().setPassword(passwordEncoder.encode(request.getPassword()));
        else
            throw new PasswordException();

        userRepository.save(user);
    }
}
