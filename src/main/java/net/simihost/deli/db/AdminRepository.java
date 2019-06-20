package net.simihost.deli.db;

import net.simihost.deli.entities.Admin;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * Created by Rashed on  21/04/2019
 *
 */
@Repository
public interface AdminRepository extends BaseRepository<Admin, Long> {

    List<Admin> findByStatus(String status);
    Admin findOneByIdAndStatus(Long id, String status);

}
