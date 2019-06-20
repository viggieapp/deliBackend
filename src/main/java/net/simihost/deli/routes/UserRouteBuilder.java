package net.simihost.deli.routes;

import net.simihost.deli.beans.MessageResponse;
import net.simihost.deli.config.AppSetting;
import net.simihost.deli.exceptions.ApplicationRuntimeException;
import net.simihost.deli.exceptions.UserNotFoundException;
import net.simihost.deli.services.account.RegistrationService;
import net.simihost.deli.services.account.UserService;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.bean.validator.BeanValidationException;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * Created by Rashed on  21/04/2019
 *
 */
@Component
public class UserRouteBuilder extends RouteBuilder {

    @Autowired
    private AppSetting appSetting;

    @Override
    public void configure() {
        String smsGatewayApiUrl = appSetting.getSmsGatewayUrl();


        onException(BeanValidationException.class)
                .handled(true)
                .to("direct:validationError")
                .log(LoggingLevel.INFO, "Validation Error Response - $simple{body}");
        onException(UserNotFoundException.class)
                .handled(true)
                .to("direct:deliError")
                .log(LoggingLevel.INFO, "Deli Error Response - $simple{body}");
        onException(ApplicationRuntimeException.class)
                .handled(true)
                .to("direct:deliError")
                .log(LoggingLevel.INFO, "Deli Error Response - $simple{body}");
        onException(Throwable.class)
                .handled(false)
                .to("direct:defaultError")
                .log(LoggingLevel.INFO, "Error Response - $simple{body}");

        from("seda:registerUser")
                .log(LoggingLevel.INFO, "Register New User Request - $simple{body}")
                .to("bean-validator://validator")
                .bean(RegistrationService.class, "processUserRegistrationRequest")
                .marshal().json(JsonLibrary.Jackson)
                .log(LoggingLevel.INFO, "Deli APP To SMS Gateway >>> (" + smsGatewayApiUrl + "): $simple{body}")
                .to(smsGatewayApiUrl)
                .unmarshal().json(JsonLibrary.Jackson, MessageResponse.class)
                .log(LoggingLevel.INFO, "Deli APP From SMS Gateway <<< $simple{body}")
                .bean(RegistrationService.class, "processUserRegistrationResponse")
                .log(LoggingLevel.INFO, "Response - $simple{body}");

        from("seda:changeUserPassword")
                .log(LoggingLevel.INFO, "Change Password Request - $simple{body}")
                .to("bean-validator://validator")
                .bean(UserService.class, "changePassword");


    }
}
