package net.simihost.deli.mapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.simihost.deli.config.AppSetting;
import net.simihost.deli.entities.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 *
 * Created by Rashed on May,2019
 *
 */
@Configurable
public class MageDeserializer<T extends Order> extends JsonDeserializer<Order> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private AppSetting appSetting;
    public ObjectMapper mapper;
    private Order object;

    private final SimpleDateFormat transDateFormat = new SimpleDateFormat("ddMMyyHHmmss");


    public MageDeserializer(AppSetting setting, T targetObject) {
        super();
        appSetting = setting;
        object = targetObject;
        mapper = new ObjectMapper();
    }

    @Override
    public Order deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        if(object != null) {
            if (object instanceof Order) {
                setGmppTransactionFields(object, node);
            }
        }

        return object;
    }

    private void setGmppTransactionFields(Order object, JsonNode node) {
        //TODO setup errorCodes and errorMessages
        logger.info("object mapper: "+object.toString());
        if (node.hasNonNull("orderId"))
            object.getOrderDetails().setOrderId(node.get("orderId").asText());
        if (node.hasNonNull("customerId"))
            object.getOrderDetails().setCustomerId( node.get("customerId").asText());
        if (node.hasNonNull("totalPaid"))
            object.getOrderDetails().setTotalPaid(node.get("totalPaid").asText());
        if (node.hasNonNull("shippingAmount"))
            object.getOrderDetails().setShippingAmount(node.get("shippingAmount").asDouble());
        if (node.hasNonNull("status"))
            object.setStatus(node.get("status").asText());
        if (node.hasNonNull("items"))
            object.getOrderDetails().setItems( node.get("items").asText());

    }
}
