package net.simihost.deli.services.admin;

import net.simihost.deli.beans.MessageRequest;
import net.simihost.deli.beans.PasswordResetRequest;
import net.simihost.deli.config.AppSetting;
import net.simihost.deli.db.UserRepository;
import net.simihost.deli.entities.account.User;
import net.simihost.deli.exceptions.UserNotFoundException;
import net.simihost.deli.helper.UtilHelper;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.commons.lang3.RandomStringUtils;
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
public class PasswordResetService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AppSetting appSetting;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private UserRepository userRepository;

    public MessageRequest processPasswordResetRequest(PasswordResetRequest request, Exchange exchange) {
        String userIdentifier = request.getUserIdentifier();

        User user = userRepository.findByMobileNo(userIdentifier);
        if(user == null)
            throw new UserNotFoundException();

        if(UtilHelper.isValidMobileNumber(request.getUserIdentifier())) {
            String newPassword = RandomStringUtils.randomAlphanumeric(appSetting.getPasswordLength());
            user.getUserDetails().setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);

            request.setNewPassword(newPassword);

            Message message = exchange.getIn();
            message.setHeader(Exchange.CONTENT_TYPE, "application/json");
            message.setHeader(Exchange.HTTP_METHOD, "POST");
            message.setHeader(Exchange.HTTP_URI, appSetting.getSmsGatewayUrl());
            message.setBody("passwordResetSMSMessage");

            return new MessageRequest();
        }
        throw new UserNotFoundException();
    }
}
