package net.simihost.deli.config;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.util.security.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

    @Autowired
    LoginService loginService;

    @Override
    protected void setupCamelContext(CamelContext camelContext) {
        camelContext.getProperties().put(Exchange.LOG_EIP_NAME, "net.simihost.camel.routes");
    }


    public HashLoginService hashLoginService( ){
        HashLoginService hashLoginService = new HashLoginService();
        String[] roles = new String[] { "ROLE_USER" };
        String[] roles1 = new String[] { "ROLE_ADMIN" };
        hashLoginService.putUser("rashed", Credential.getCredential("123456"), roles);
        hashLoginService.putUser("rashed1", Credential.getCredential("1234567"), roles1);
        hashLoginService.setName("apimanrealm");
        return hashLoginService;
    }

    public Constraint constraint(){
        Constraint constraint=new Constraint();
        constraint.setName("BASIC");
        constraint.setAuthenticate(true);
        constraint.setRoles(new String[]{"ROLE_USER","ROLE_ADMIN"});
        System.out.println("3#######################");
        return constraint;
    }

    public ConstraintMapping constraintMapping(){
        ConstraintMapping constraintMapping=new ConstraintMapping();
        constraintMapping.setPathSpec("/*");
        constraintMapping.setConstraint(constraint());
        System.out.println("2#######################");
        return constraintMapping;
    }

    @Bean(name = "securityHandler")
    public  ConstraintSecurityHandler createSecurityHandler() {
        ConstraintSecurityHandler handler = new ConstraintSecurityHandler();
        handler.addConstraintMapping(constraintMapping());
        handler.setAuthenticator(new BasicAuthenticator());
        handler.setRealmName("apimanrealm");
        handler.setLoginService(loginService);
        return handler;
    }


//    @DependsOn(value = "camelContext")
//    @Bean(name = "securityHandler")
//    public ConstraintSecurityHandler securityHandler( ){
//        ConstraintSecurityHandler securityHandler=new ConstraintSecurityHandler();
//        securityHandler.setLoginService(new HardcodedLoginService());
//        securityHandler.setAuthenticator(new BasicAuthenticator());
//        securityHandler.addConstraintMapping(constraintMapping());
//        System.out.println("1#######################"+securityHandler.isStarted());
//        return  securityHandler;
//    }



   /* @Autowired
    private Environment environment;

    @DependsOn(value = "camelContext")
    @Bean(name = "quartz2")
    public QuartzComponent quartz2(CamelContext camelContext) throws Exception{
        QuartzComponent quartzComponent = new QuartzComponent(camelContext);
        quartzComponent.setPropertiesFile(environment.getProperty("quartz.properties.file"));
        return quartzComponent;
    }*/

}
