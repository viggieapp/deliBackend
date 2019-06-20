package net.simihost.deli.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * Created by Rashed on 25/05/2019
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageResponse {

    private String responseStatus;

    @JsonProperty("status")
    public String getResponseStatus() {
        return responseStatus;
    }

    @JsonIgnore
    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    @Override
    public String toString() {
        return "MessageResponse{" +
                "responseStatus='" + responseStatus + '\'' +
                '}';
    }
}
