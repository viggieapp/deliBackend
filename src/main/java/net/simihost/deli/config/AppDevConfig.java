package net.simihost.deli.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile(value = "development")
@PropertySource({"classpath:application-dev.properties", "classpath:quartz/quartz-dev.properties"})
public class AppDevConfig {

}
