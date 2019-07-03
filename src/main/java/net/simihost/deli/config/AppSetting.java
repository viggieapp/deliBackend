package net.simihost.deli.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppSetting {

    private final static String RESTLET_HOST = "restlet.host";
    private final static String RESTLET_PORT = "restlet.port";
    private final static String API_ORDER_PATH = "api.orders.path";
    private final static String API_ADMIN_PATH = "api.admin.path";
    private final static String API_USER_PATH = "api.user.path";
    private final static String API_ADMINS_PATH = "api.admins.path";
    private final static String API_APP_USERS_PATH = "api.app.users.path";
    private final static String SMS_GATEWAY_URL = "sms.gateway.url";
    private final static String Mage_GATEWAY_API_BASE_URL = "mage.";
    private final static String PASSWORD_LENGTH = "password.length";
    private final static String REFRESH_KEYS_CRON_EXPRESSION = "quartz.cron.refreshKeys";

    private final static String API_MAGE_NEW_ORDER_PATH = "api.mage.getNewOrder.path";
    private final static String MAGE_GET_NEW_ORDER = "mage.getNewOrder";


    @Autowired
    private Environment env;

    public String getRestletHost() {
        return env.getRequiredProperty(RESTLET_HOST);
    }
    public String getRestletPort() {
        return env.getRequiredProperty(RESTLET_PORT);
    }
    public String getApiOrderPath() {
        return env.getRequiredProperty(API_ORDER_PATH);
    }
    public String getApiAdminPath() {
        return env.getRequiredProperty(API_ADMIN_PATH);
    }
    public String getApiUserPath() {
        return env.getRequiredProperty(API_USER_PATH);
    }
    public String getApiAdminsPath() {
        return env.getProperty(API_ADMINS_PATH);
    }
    public String getApiAppUsersPath() {
        return env.getProperty(API_APP_USERS_PATH);
    }
    public String getSmsGatewayUrl() {
        return env.getRequiredProperty(SMS_GATEWAY_URL);
    }
    public String getMageGatewayApiUrl(String className) {
        return env.getRequiredProperty(Mage_GATEWAY_API_BASE_URL.concat(className));
    }
    public Integer getPasswordLength() {
        return env.getProperty(PASSWORD_LENGTH, Integer.class, 6);
    }

    public String getRefreshKeysCronExpression() {
        return env.getRequiredProperty(REFRESH_KEYS_CRON_EXPRESSION);
    }

    public String getApiMageNewOrderPath() {
        return env.getRequiredProperty(API_MAGE_NEW_ORDER_PATH);
    }

    public String getMageNewOrderAPIUrl() {
        return env.getRequiredProperty(MAGE_GET_NEW_ORDER);
    }
}
