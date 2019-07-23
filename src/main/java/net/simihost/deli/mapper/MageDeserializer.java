package net.simihost.deli.mapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
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

    private void setGmppTransactionFields(Order object, JsonNode node1) {
        //TODO setup errorCodes and errorMessages
        logger.info("object mapper: "+object.toString());

        if(node1.hasNonNull("items")){
            ArrayNode node2= (ArrayNode) node1.get("items");
            for(JsonNode node:node2){
                if (node.hasNonNull("entity_id"))
                    object.getOrderDetails().setOrderId(node.get("entity_id").asText());
                if (node.hasNonNull("customer_id"))
                    object.getOrderDetails().setCustomerId( node.get("customer_id").asText());
                if (node.hasNonNull("created_at"))
                    object.getOrderDetails().setCreated_at( node.get("created_at").asText());
                if (node.hasNonNull("total_due"))
                    object.getOrderDetails().setTotalPaid(node.get("total_due").asText());
                if (node.hasNonNull("shipping_amount"))
                    object.getOrderDetails().setShippingAmount(node.get("shipping_amount").asDouble());
                if (node.hasNonNull("status"))
                    object.setStatus(node.get("status").asText());
                if (node.hasNonNull("items")){
                    ArrayNode node3= (ArrayNode) node.get("items");
                    for(JsonNode node4:node3){
                        object.getOrderDetails().setItems(node4.toString());
                    }
                }
            }

        }
    }
}
