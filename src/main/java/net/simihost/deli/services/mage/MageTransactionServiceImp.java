package net.simihost.deli.services.mage;

import net.simihost.deli.db.OrderRepository;
import net.simihost.deli.entities.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * Created by Rashed on May,2019
 *
 */
@org.springframework.stereotype.Service
public class MageTransactionServiceImp implements MageTransactionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OrderRepository orderRepository;

    @Override
    public <T extends Order> T processPreSend(T transaction)  {
        logger.debug("Saving transaction request{}", transaction.toString());
        return transaction;
    }

    @Override
    public <T extends Order> T processPostSend(T transaction) {
        storeTransaction(transaction);
        return transaction;
    }


    @Transactional()
    protected  <T extends Order> T storeTransaction(T transaction) {
        return orderRepository.save(transaction);
    }
}
