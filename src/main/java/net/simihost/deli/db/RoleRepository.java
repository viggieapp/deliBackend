package net.simihost.deli.db;

import net.simihost.deli.entities.Role;
import org.springframework.stereotype.Repository;

/**
 *
 * Created by Rashed on  23/04/2019
 *
 */
@Repository
public interface RoleRepository extends BaseRepository<Role, Long>{

    Role findByRoleName(String roleName);

}
