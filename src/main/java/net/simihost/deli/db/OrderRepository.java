package net.simihost.deli.db;

import net.simihost.deli.entities.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * Created by Rashed on  21/04/2019
 *
 */
@Repository
public interface OrderRepository extends BaseRepository<Order, Long> {

    List<Order> findByStatus(String status);
    Order findOneByIdAndStatus(Long id, String status);

}
