package net.simihost.deli.routes;

import net.simihost.deli.beans.MessageResponse;
import net.simihost.deli.config.AppSetting;
import net.simihost.deli.exceptions.AdminNotFoundException;
import net.simihost.deli.exceptions.ApplicationRuntimeException;
import net.simihost.deli.services.account.UserService;
import net.simihost.deli.services.admin.AdminService;
import net.simihost.deli.services.admin.PasswordResetService;
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
public class AdminRouteBuilder extends RouteBuilder {

    @Autowired
    private AppSetting appSetting;

    @Override
    public void configure() throws Exception {
        String smsGatewayApiUrl = appSetting.getSmsGatewayUrl();

        onException(BeanValidationException.class)
                .handled(true)
                .to("direct:validationError")
                .log(LoggingLevel.INFO, "Validation Error Response - $simple{body}");
        onException(AdminNotFoundException.class)
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

        from("seda:getAdmins")
                .log(LoggingLevel.INFO, "Get All Admins")
                .bean(AdminService.class, "getAdmins")
                .log(LoggingLevel.INFO, "Get Admins Response - $simple{body}");
        from("seda:getAdmin")
                .log(LoggingLevel.INFO, "Get Admin with Id - $simple{header.id}")
                .bean(AdminService.class, "getAdmin(${header.id})")
                .log(LoggingLevel.INFO, "Get Admin Response  - $simple{body}");
        from("seda:addAdmin")
                .log(LoggingLevel.INFO, "Add new Admin - $simple{body}")
                .to("bean-validator://validator")
                .bean(AdminService.class, "addAdmin");
        from("seda:updateAdmin")
                .log(LoggingLevel.INFO, "Update Admin with Id - $simple{header.id}")
                .to("bean-validator://validator")
                .bean(AdminService.class, "updateAdmin(${header.id}, ${body})")
                .log(LoggingLevel.INFO, "Update Admin Response - $simple{body}");
        from("seda:deleteAdmin")
                .log(LoggingLevel.INFO, "Delete Admin with Id - $simple{header.id}")
                .bean(AdminService.class, "deleteAdmin(${header.id})");

        from("seda:getUsers")
                .log(LoggingLevel.INFO, "Get All Users")
                .bean(UserService.class, "getUsers")
                .log(LoggingLevel.INFO, "Get Users Response - $simple{body}");
        from("seda:getUser")
                .log(LoggingLevel.INFO, "Get User with Id - $simple{header.id}")
                .bean(UserService.class, "getUser(${header.id})")
                .log(LoggingLevel.INFO, "Get User Response  - $simple{body}");
        from("seda:addUser")
                .log(LoggingLevel.INFO, "Add New User - $simple{body}")
                .to("bean-validator://validator")
                .bean(UserService.class, "addUser");
        from("seda:updateUser")
                .log(LoggingLevel.INFO, "Update User with Id - $simple{header.id}")
                .to("bean-validator://validator")
                .bean(UserService.class, "updateUser(${header.id}, ${body})")
                .log(LoggingLevel.INFO, "Update User Response - $simple{body}");
        from("seda:deleteUser")
                .log(LoggingLevel.INFO, "Delete User with Id - $simple{header.id}")
                .bean(UserService.class, "deleteUser(${header.id})");

        from("seda:resetUserPassword")
                .log(LoggingLevel.INFO, "Reset Password Request - $simple{body}")
                .to("bean-validator://validator")
                .bean(PasswordResetService.class, "processPasswordResetRequest")
                .marshal().json(JsonLibrary.Jackson)
                .log(LoggingLevel.INFO, "Deli APP To SMS Gateway >>> ( " + smsGatewayApiUrl + " ): $simple{body}")
                .to(smsGatewayApiUrl)
                .unmarshal().json(JsonLibrary.Jackson, MessageResponse.class)
                .log(LoggingLevel.INFO, "Deli APP From SMS Gateway <<< $simple{body}");

    }
}
