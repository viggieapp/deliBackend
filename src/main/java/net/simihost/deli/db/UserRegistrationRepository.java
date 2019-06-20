package net.simihost.deli.db;

import net.simihost.deli.entities.UserRegistration;
import org.springframework.stereotype.Repository;

/**
 *
 * Created by Rashed on  25/05/2019
 *
 */
@Repository
public interface UserRegistrationRepository extends BaseRepository<UserRegistration, Long> {

}
