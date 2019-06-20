package net.simihost.deli.services.account;

import net.simihost.deli.beans.MessageRequest;
import net.simihost.deli.beans.RegistrationRequest;
import net.simihost.deli.config.AppSetting;
import net.simihost.deli.db.RoleRepository;
import net.simihost.deli.db.UserRegistrationRepository;
import net.simihost.deli.db.UserRepository;
import net.simihost.deli.entities.Role;
import net.simihost.deli.entities.UserRegistration;
import net.simihost.deli.entities.account.User;
import net.simihost.deli.exceptions.DuplicateUserException;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Created by Rashed on  23/05/2019
 *
 */
@Service
@Transactional
public class RegistrationService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AppSetting appSetting;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRegistrationRepository userRegistrationRepository;


    public MessageRequest processUserRegistrationRequest(RegistrationRequest request, Exchange exchange) {

        return null;
    }

    public void processUserRegistrationResponse(Exchange exchange) {
        exchange.getIn().setBody(exchange.getProperty("registration"));
    }



    private UserRegistration registerUser(UserRegistration registration) {
        String encryptedPassword = passwordEncoder.encode(registration.getUserDetails().getPassword());
        String encryptedConfirmedPassword = passwordEncoder.encode(registration.getUserDetails().getConfirmedPassword());
        registration.getUserDetails().setPassword(encryptedPassword);
        registration.getUserDetails().setConfirmedPassword(encryptedConfirmedPassword);

        if (userRepository.findByMobileNo(registration.getUserDetails().getMobileNo()) != null)
            throw new DuplicateUserException();

        if (userRepository.findByEmail(registration.getUserDetails().getEmail()) != null)
            throw new DuplicateUserException();

        registration.setStatus("Inactive");
        userRegistrationRepository.save(registration);
        return registration;
    }

    private User mapRegistrationToNewUser(UserRegistration registeration) {
        User user = new User(registeration.getUserDetails());
        user.setRegistrationId(registeration.getId());
        user.setStatus("Active");

        //Default user role
        Role userRole = roleRepository.findByRoleName("ROLE_USER");
        user.addRoles(userRole);

        return user;
    }


}
