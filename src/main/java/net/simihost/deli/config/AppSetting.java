package net.simihost.deli.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppSetting {

    private final static String APPLICATION_ID = "application.id";
    private final static String GMPP_APPLICATION_ID = "gmpp.application.id";
    private final static String GMPP_APP_PASSWORD = "gmpp.app.password";

    private final static String RESTLET_HOST = "restlet.host";
    private final static String RESTLET_PORT = "restlet.port";

    private final static String API_ORDER_PATH = "api.order.path";
    private final static String API_ADMIN_PATH = "api.admin.path";
    private final static String API_USER_PATH = "api.user.path";
    private final static String API_ADMINS_PATH = "api.admins.path";
    private final static String API_APP_USERS_PATH = "api.app.users.path";
    private final static String API_CONSUMER_PATH = "api.consumer.path";
    private final static String API_GMPP_PATH = "api.gmpp.path";

    private final static String SMS_GATEWAY_URL = "sms.gateway.url";

    private final static String GENERATE_IPIN_USERNAME = "ipin.generate.username";
    private final static String GENERATE_IPIN_PASSWORD = "ipin.generate.password";

    private final static String CONSUMER_PAYMENT_GATEWAY_API_BASE_URL = "consumer.";
    private final static String GMPP_GATEWAY_API_BASE_URL = "gmpp.";
//    private final static String COREBANKING_PAYMENT_GATEWAY_API_BASE_URL = "corebanking.payment.gateway.api.base.url";

    private final static String PASSWORD_LENGTH = "password.length";
    private final static String TOKEN_LENGTH = "token.length";
    private final static String TOKEN_PHONE_REGISTRATION_DURATION = "token.phoneRegistration.time.to.live.in.minutes";
    private final static String TOKEN_LOST_PASSWORD_EXPIRY_DURATION = "token.lostPassword.time.to.live.in.minutes";


    private final static String TRANS_HISTORY_PAGE_SIZE = "trans.history.all.page";
    private final static String REFRESH_KEYS_CRON_EXPRESSION = "quartz.cron.refreshKeys";

    @Autowired
    private Environment env;

    public String getActiveProfile() {
        String[] activeProfiles = env.getActiveProfiles();
        return activeProfiles.length > 0 ? activeProfiles[0]: "development";
    }

    public String getApplicationId() {
        return env.getRequiredProperty(APPLICATION_ID);
    }

    public String getGmppApplicationId() {
        return env.getRequiredProperty(GMPP_APPLICATION_ID);
    }

    public String getGmppAppPassword() {
        return env.getRequiredProperty(GMPP_APP_PASSWORD);
    }

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

    public String getApiConsumerPath() {
        return env.getRequiredProperty(API_CONSUMER_PATH);
    }

    public String getApiGmppPath() {
        return env.getRequiredProperty(API_GMPP_PATH);
    }

    public String getSmsGatewayUrl() {
        return env.getRequiredProperty(SMS_GATEWAY_URL);
    }

    public String getGenerateIpinUsername() {
        return env.getRequiredProperty(GENERATE_IPIN_USERNAME);
    }

    public String getGenerateIpinPassword() {
        return env.getRequiredProperty(GENERATE_IPIN_PASSWORD);
    }

    public String getConsumerPaymentGatewayApiUrl(String className) {
        return env.getRequiredProperty(CONSUMER_PAYMENT_GATEWAY_API_BASE_URL.concat(className));
    }

    public String getGmppGatewayApiUrl(String className) {
        return env.getRequiredProperty(GMPP_GATEWAY_API_BASE_URL.concat(className));
    }

    public Integer getPasswordLength() {
        return env.getProperty(PASSWORD_LENGTH, Integer.class, 6);
    }

    public Integer getTokenLength() {
        return env.getProperty(TOKEN_LENGTH, Integer.class, 5);
    }

    public Integer getPhoneRegistrationTokenExpiryDurationInMinutes() {
        return env.getProperty(TOKEN_PHONE_REGISTRATION_DURATION, Integer.class, 15);
    }

    public Integer getLostPasswordTokenExpiryDurationInMinutes() {
        return env.getProperty(TOKEN_LOST_PASSWORD_EXPIRY_DURATION, Integer.class, 60);
    }

    public Integer getTransHistoryPageSize() {
        return env.getProperty(TRANS_HISTORY_PAGE_SIZE, Integer.class, 5);
    }

    public String getRefreshKeysCronExpression() {
        return env.getRequiredProperty(REFRESH_KEYS_CRON_EXPRESSION);
    }
}
