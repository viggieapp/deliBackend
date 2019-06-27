package net.simihost.deli.routes;

import net.simihost.deli.beans.*;
import net.simihost.deli.beans.account.UserDTO;
import net.simihost.deli.config.AppSetting;
import net.simihost.deli.mapper.MageDataFormat;
import net.simihost.deli.services.mage.MageService;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
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
public class GenericRouteBuilder extends RouteBuilder {

    @Autowired
    private AppSetting appSetting;

    @Autowired
    private MageDataFormat mageDataFormat;

    @Override
    public void configure() {

        String mageOrderApiUrl = appSetting.getMageGatewayApiUrl("getOrder");
        restConfiguration().component("restlet")
                .host(appSetting.getRestletHost())
                .port(appSetting.getRestletPort())
                .enableCORS(true)
                .corsHeaderProperty("Access-Control-Allow-Origin","*")
                .bindingMode(RestBindingMode.json);

        String mageNewOrderAPIPath = appSetting.getApiMageNewOrderPath();


        String orderAPIPath = appSetting.getApiOrderPath();
        String adminAPIPath = appSetting.getApiAdminPath();
        String userAPIPath = appSetting.getApiUserPath();
        String adminUsersAPIPath = adminAPIPath + appSetting.getApiAdminsPath();
        String appUsersAPIPath = adminAPIPath + appSetting.getApiAppUsersPath();

        //MOBILE APP ROUTES
        rest(mageNewOrderAPIPath)
                //1. Get New Order
                .get("{id}")
                .produces("application/json")
                .outType(OrderDTO.class)
                .to("seda:newOrderFromMage");

        //OPERATIONS ROUTES
        rest(orderAPIPath)
                //1. Get All Orders
                .get()
                .produces("application/json")
                .to("seda:getOrders")
                //2. Get Order
                .get("{id}")
                .produces("application/json")
                .outType(OrderDTO.class)
                .to("seda:getOrder")
                //3. Add Order
                .post()
                .consumes("application/json").produces("application/json")
                .type(OrderDTO.class).outType(OrderDTO.class)
                .to("seda:addOrder")
                //4. Edit Order
                .put("{id}")
                .consumes("application/json").produces("application/json")
                .type(OrderDTO.class).outType(OrderDTO.class)
                .to("seda:updateOrder")
                //5. Delete Order
                .delete("{id}")
                .to("seda:deleteOrder");

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
                //.to("seda:quartzJob")
                .log(LoggingLevel.INFO, "QUARTZ >>><<< QUARTZ");

        from("seda:quartzJob")
                .log(LoggingLevel.INFO, "Get Orders DTO Request")
                .setHeader("Authorization", constant("Bearer 7kod7nurljeab3nkqvflyqe1m9lq4vqy"))
                .process(new Processor(){
                    public void process(Exchange exchange) throws Exception {
                        exchange.getIn().setHeader(Exchange.AUTHENTICATION,"Bearer 7kod7nurljeab3nkqvflyqe1m9lq4vqy");
                    }})
                .to(mageOrderApiUrl)
                .unmarshal(mageDataFormat)
                .log(LoggingLevel.INFO, "Get Orders Response  - $simple{body}")
                .bean(MageService.class, "processTransactionResponse");

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
