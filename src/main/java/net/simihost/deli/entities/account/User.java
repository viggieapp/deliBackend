package net.simihost.deli.entities.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * Created by Rashed on  19/03/2019
 *
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "users")
public class User extends BaseUser {

    private static final long serialVersionUID = 3344585642081549555L;

    public User() {
        super();
    }

    public User(UserDetails details) {
        super(details);
    }

    @Override
    public String toString() {
        return "User{}";
    }
}
