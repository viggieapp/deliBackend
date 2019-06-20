package net.simihost.deli.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * Created by Rashed on  19/03/2019
 *
 */
@Configuration
@Profile(value = "production")
@PropertySource({"classpath:application-prod.properties", "classpath:quartz/quartz-prod.properties"})
public class AppProdConfig {

}
