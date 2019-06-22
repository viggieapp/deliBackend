package net.simihost.deli.services.mage;

import net.simihost.deli.beans.OrderDTO;
import net.simihost.deli.entities.Order;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Created by Rashed on May,2019
 *
 */
@Service
public class MageService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MageTransactionService mageTransactionService;

    public <DTO extends OrderDTO, T extends Order> DTO
    processTransactionResponse(T entity, Exchange exchange) {
        logger.trace("Response Entity : {}", entity);
        DTO response = (DTO) exchange.getProperty("requestDTO");

//        logger.trace("Request object in exchange : {}, {}", response.getClass().getSimpleName(), response);
        mageTransactionService.processPostSend(entity);
        return response;
    }

}
