package net.simihost.deli.routes;

import net.simihost.deli.beans.*;
import net.simihost.deli.beans.account.UserDTO;
import net.simihost.deli.config.AppSetting;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * Created by Rashed on  18/03/2019
 *
 */
@Component
public class GenericWmeRouteBuilder extends RouteBuilder {

    @Autowired
    private AppSetting appSetting;

    @Override
    public void configure() {

        restConfiguration().component("restlet")
                .host(appSetting.getRestletHost()).port(appSetting.getRestletPort())
                .bindingMode(RestBindingMode.json);

        String statusAPIPath = appSetting.getApiStatusPath();
        String adminAPIPath = appSetting.getApiAdminPath();
        String userAPIPath = appSetting.getApiUserPath();
        String adminUsersAPIPath = adminAPIPath + appSetting.getApiAdminsPath();
        String appUsersAPIPath = adminAPIPath + appSetting.getApiAppUsersPath();


        // ADMIN USERS ROUTES
        rest(adminUsersAPIPath)
                //1. Get All Admins
                .get()
                    .produces("application/json")
                    .to("seda:getAdmins")
                //2. Get Admin
                .get("{id}")
                    .produces("application/json")
                    .outType(AdminDTO.class)
                    .to("seda:getAdmin")
                //3. Add Admin
                .post()
                    .consumes("application/json").produces("application/json")
                    .type(AdminDTO.class).outType(AdminDTO.class)
                    .to("seda:addAdmin")
                //4. Edit Admin
                .put("{id}")
                    .consumes("application/json").produces("application/json")
                    .type(AdminDTO.class).outType(AdminDTO.class)
                    .to("seda:updateAdmin")
                //5. Delete Admin
                .delete("{id}")
                    .to("seda:deleteAdmin");

        // APP USERS ROUTES
        rest(appUsersAPIPath)
                //1. Get All Users
                .get()
                    .produces("application/json")
                    .to("seda:getUsers")
                //2. Get User
                .get("{id}")
                    .produces("application/json")
                    .outType(UserDTO.class)
                    .to("seda:getUser")
                //3. Add User
                .post()
                    .consumes("application/json").produces("application/json")
                    .type(UserDTO.class).outType(UserDTO.class)
                    .to("seda:addUser")
                //4. Edit User
                .put("{id}")
                    .consumes("application/json").produces("application/json")
                    .type(UserDTO.class).outType(UserDTO.class)
                    .to("seda:updateUser")
                //5. Delete User
                .delete("{id}")
                    .to("seda:deleteUser")
                //6. Reset User Password
                .post("/password/reset")
                    .consumes("application/json").produces("application/json")
                    .type(PasswordResetRequest.class).outType(MessageResponse.class)
                    .to("seda:resetUserPassword");

        rest(userAPIPath)
                //1. Register New User
                .post("/register")
                    .consumes("application/json").produces("application/json")
                    .type(RegistrationRequest.class).outType(RegistrationRequest.class)
                    .to("seda:registerUser")

                //4. Change User Password
                .post("/password/change")
                    .consumes("application/json")
                    .type(ChangePasswordRequest.class)
                    .to("seda:changeUserPassword");


        // QUARTZ JOB
        from("quartz2://refreshing-keys-quartz?cron=".concat(appSetting.getRefreshKeysCronExpression()))
                .to("seda:quartzJob");

        from("seda:quartzJob")
                .log(LoggingLevel.INFO, "QUARTZ >>><<< QUARTZ");

        // EXCEPTION ROUTES
        from("direct:validationError")
                .transform().simple("{\n" +
                "    \"responseStatus\":\"Invalid\",\n" +
                "    \"responseCode\":82,\n" +
                "    \"responseMessage\":\"${exception.message}\"\n" +
                "}");

        from("direct:deliError")
                .transform().simple("{\n" +
                "    \"responseStatus\":\"Failed\",\n" +
                "    \"responseCode\":\"82\",\n" +
                "    \"responseMessage\":\"${exception.message}\"\n" +
                "}");

        from("direct:defaultError")
                .transform().simple("{\n" +
                "    \"responseStatus\":\"Failed\",\n" +
                "    \"responseCode\":54,\n" +
                "    \"responseMessage\":\"${exception.message}\"\n" +
                "}");
    }
}