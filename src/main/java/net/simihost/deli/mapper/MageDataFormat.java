package net.simihost.deli.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import net.simihost.deli.config.AppSetting;
import net.simihost.deli.entities.Order;
import net.simihost.deli.entities.OrderDetails;
import org.apache.camel.Exchange;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.support.ServiceSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * Created by Rashed on May,2019
 *
 */
@Service
public class MageDataFormat extends ServiceSupport implements DataFormat {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AppSetting appSetting;

    @Override
    public void marshal(Exchange exchange, Object object, OutputStream outputStream) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Order.class, new MageSerializer<>(appSetting));
        mapper.registerModule(module);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String request = mapper.writeValueAsString(object);
        logger.debug("Deli To Mage ({}): {}", exchange.getIn().getHeader(Exchange.HTTP_URI), request);
        outputStream.write(request.getBytes());
    }

    @Override
    public Object unmarshal(Exchange exchange, InputStream inputStream) throws Exception {
        String response = exchange.getContext().getTypeConverter().mandatoryConvertTo(String.class, inputStream);
        logger.debug("Deli From Mage : {}",response);
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        Order order= new Order();
        order.setOrderDetails(new OrderDetails());
        module.addDeserializer(Order.class, new MageDeserializer<>(appSetting, order));
        mapper.registerModule(module);
        return mapper.readValue(response, Order.class);
    }

    @Override
    protected void doStart() throws Exception {}

    @Override
    protected void doStop() throws Exception {}
}
