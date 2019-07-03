package net.simihost.deli.config;

/**
 * Created by Rashed on Jul,2019
 */

import net.simihost.deli.entities.Role;
import net.simihost.deli.entities.account.User;
import net.simihost.deli.services.account.UserService;
import org.eclipse.jetty.security.MappedLoginService;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.util.security.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginService extends MappedLoginService {

    @Autowired
    UserService getUser;

    public LoginService() {

        setName("apimanrealm");

    }

    @Override

    protected UserIdentity loadUser(final String username) {
        // make service call that will look up a user by username
        // this will be called once time at init so it doesn't make sense
        return user(username);
    }

    @Override

    protected void loadUsers() throws IOException {

        // make service call to load users that will be cached by Jetty (alternatively you can call

        // _users.clear() here if you want to use another user caching mechanism like JPA- it will cause loadUser to be called)

       // _users.putAll(loadMyUserMap());

    }

    @Override

    public UserIdentity login(final String username, final Object credentials) {
        // do something here if you need to validate a user when they login
        user(username);
        return super.login(username, credentials);
    }

    @Override

    public void logout(final UserIdentity identity) {
        // do something here if you need to invalidate a user when they logout
        super.logout(identity);
    }

    private UserIdentity user(String email){
        User user = getUser.getUserByEmail(email);
        if(user!=null){
            final Credential cred = Credential.getCredential(user.getUserDetails().getPassword());
            String rols[] = new String[user.getRoles().size()];
            int c=0;
            for (Role r : user.getRoles()) {
                rols[c]=r.getRoleName();
                c++;
            }
            return putUser(user.getUserDetails().getEmail(), cred,rols);
        }
        return putUser("", null,null);

    }

}