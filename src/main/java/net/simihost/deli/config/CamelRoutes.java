package net.simihost.deli.config;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * Created by Rashed on  18/03/2019
 *
 */
@Configuration
@ComponentScan("net.simihost.deli.routes")
public class CamelRoutes extends CamelConfiguration {

    @Override
    protected void setupCamelContext(CamelContext camelContext) {
        camelContext.getProperties().put(Exchange.LOG_EIP_NAME, "net.simihost.camel.routes");
    }

    /*@Autowired
    private Environment environment;

    @DependsOn(value = "camelContext")
    @Bean(name = "quartz2")
    public QuartzComponent quartz2(CamelContext camelContext) throws Exception{
        QuartzComponent quartzComponent = new QuartzComponent(camelContext);
        quartzComponent.setPropertiesFile(environment.getProperty("quartz.properties.file"));
        return quartzComponent;
    }*/

}
