package net.simihost.deli.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 *
 * @author Ruba Hamad on 25/05/2019
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageRequest {

    private String applicationId;
    private String recipient;
    private Map<String, String> content;
    private String messageType;
    private String environment;

    public MessageRequest() {}

    public MessageRequest(String applicationId, String recipient, Map<String,String> content, String messageType, String environment) {
        this.applicationId = applicationId;
        this.recipient = recipient;
        this.content = content;
        this.messageType = messageType;
        this.environment = environment;
    }

    @JsonProperty("from")
    public String getApplicationId() {
        return applicationId;
    }

    @JsonIgnore
    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    @JsonProperty("to")
    public String getRecipient() {
        return recipient;
    }

    @JsonIgnore
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @JsonProperty("content")
    public Map<String,String> getContent() {
        return content;
    }

    @JsonIgnore
    public void setContent(Map<String,String> content) {
        this.content = content;
    }

    @JsonProperty("msgtype")
    public String getMessageType() {
        return messageType;
    }

    @JsonIgnore
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @JsonProperty("fromenv")
    public String getEnvironment() {
        return environment;
    }

    @JsonIgnore
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    @Override
    public String toString() {
        return "MessageRequest{" +
                "applicationId='" + applicationId + '\'' +
                ", recipient='" + recipient + '\'' +
                ", content=" + content +
                ", messageType='" + messageType + '\'' +
                ", environment='" + environment + '\'' +
                '}';
    }
}
