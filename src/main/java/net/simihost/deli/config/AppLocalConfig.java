package net.simihost.deli.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile(value = {"local","default"})
@PropertySource({"classpath:application-local.properties", "classpath:quartz/quartz-local.properties"})
public class AppLocalConfig {

}
