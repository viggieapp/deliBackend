package net.simihost.deli.db;

import net.simihost.deli.entities.account.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * Created by Rashed on  19/03/2019
 *
 */
@Repository
public interface UserRepository extends BaseRepository<User, Long> {

    List<User> findByStatus(String status);

    User findOneByIdAndStatus(Long id, String status);

    @Query("from User u where u.userDetails.mobileNo = :mobileNo")
    User findByMobileNo(@Param("mobileNo") String mobileNo);

    @Query("from User u where u.userDetails.email = :email")
    User findByEmail(@Param("email") String email);

    @Query("from User u where u.userDetails.email = :identifier OR u.userDetails.mobileNo = :identifier")
    User findByMobileNoOrEmail(@Param("identifier") String identifier);

    User findByRegistrationId(Long registrationId);

}
