package net.simihost.deli.mapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
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
public class MageSerializer<T extends Order> extends JsonSerializer<T> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private AppSetting appSetting;

    private final SimpleDateFormat transDateFormat = new SimpleDateFormat("ddMMyyHHmmss");

    public MageSerializer(AppSetting appSetting) {
        this.appSetting = appSetting;
    }

    @Override
    public void serialize(T object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();

        if (object != null) {
            if (object instanceof Order) {
                writeTransactionFields(object, jsonGenerator);
            }
        }

        jsonGenerator.writeEndObject();
    }

    private void writeTransactionFields(Order object, JsonGenerator jsonGen) throws IOException {

    }


    private void writeStringField(JsonGenerator jsonGen, String fieldName, String value) throws IOException {
        if(value == null) return;
        jsonGen.writeStringField(fieldName, value);
    }



}
