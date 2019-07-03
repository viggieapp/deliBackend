package net.simihost.deli.routes;

import net.simihost.deli.config.AppSetting;
import net.simihost.deli.exceptions.AdminNotFoundException;
import net.simihost.deli.exceptions.ApplicationRuntimeException;
import net.simihost.deli.mapper.MageDataFormat;
import net.simihost.deli.services.admin.OrderService;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
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
public class AppRouteBuilder extends RouteBuilder {

    @Autowired
    private AppSetting appSetting;

    @Autowired
    private MageDataFormat mageDataFormat;

    @Override
    public void configure() throws Exception {
        String queryParams="?" +
                "searchCriteria[filterGroups][1][filters][0][field]=entity_id" +
                "&searchCriteria[filterGroups][1][filters][0][conditionType]=eq" +
                "&searchCriteria[filterGroups][1][filters][0][value]=";
        String getMageNewOrderAPIUrl = appSetting.getMageNewOrderAPIUrl();
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

        from("seda:newOrderFromMage")
                //Get New Order From Mage
                .log(LoggingLevel.INFO, " New Order From Mage ")
                .setHeader("Authorization", constant("Bearer 7kod7nurljeab3nkqvflyqe1m9lq4vqy"))
                .process(new Processor(){
                    public void process(Exchange exchange) throws Exception {
                        exchange.getIn().setHeader(Exchange.HTTP_URI,getMageNewOrderAPIUrl+queryParams+exchange.getIn().getHeader("id"));
                        exchange.setProperty("url",getMageNewOrderAPIUrl+queryParams+exchange.getIn().getHeader("id"));
                    }})
                .marshal(mageDataFormat)
                .recipientList(exchangeProperty("url"))
                .unmarshal(mageDataFormat)
                .log(LoggingLevel.INFO, " New Order $simple{body} From mage")
                .bean(OrderService.class, "newOrderFromMage($simple{body})")
                .log(LoggingLevel.INFO, " New Order Response  - $simple{body}");

    }
}
