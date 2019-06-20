package net.simihost.deli.routes;

import net.simihost.deli.config.AppSetting;
import net.simihost.deli.exceptions.AdminNotFoundException;
import net.simihost.deli.exceptions.ApplicationRuntimeException;
import net.simihost.deli.services.admin.OrderService;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.bean.validator.BeanValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * Created by Rashed on  21/04/2019
 *
 */
@Component
public class OrderRouteBuilder extends RouteBuilder {

    @Autowired
    private AppSetting appSetting;

    @Override
    public void configure() throws Exception {

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

        from("seda:getOrders")
                .log(LoggingLevel.INFO, "Get All Orders")
                .bean(OrderService.class, "getOrders")
                .log(LoggingLevel.INFO, "Get Orders Response - $simple{body}");
        from("seda:getOrder")
                .log(LoggingLevel.INFO, "Get Order with Id - $simple{header.id}")
                .bean(OrderService.class, "getOrder(${header.id})")
                .log(LoggingLevel.INFO, "Get Order Response  - $simple{body}");
        from("seda:addOrder")
                .log(LoggingLevel.INFO, "Add new Order - $simple{body}")
                .to("bean-validator://validator")
                .bean(OrderService.class, "addOrder");
        from("seda:updateOrder")
                .log(LoggingLevel.INFO, "Update Order with Id - $simple{header.id}")
                .to("bean-validator://validator")
                .bean(OrderService.class, "updateOrder(${header.id}, ${body})")
                .log(LoggingLevel.INFO, "Update Order Response - $simple{body}");
        from("seda:deleteOrder")
                .log(LoggingLevel.INFO, "Delete Order with Id - $simple{header.id}")
                .bean(OrderService.class, "deleteOrder(${header.id})");

    }
}
