package net.simihost.deli.services.mage;

import net.simihost.deli.entities.Order;

/**
 *
 * Created by Rashed on May,2019
 *
 */
public interface MageTransactionService {

    <T extends Order> T processPreSend(T transaction);
    <T extends Order> T processPostSend(T transaction);
}
