package com.eoutlet.Eoutlet.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
 @JsonPropertyOrder({
    "status",
    "message"
})
public class Message {

    @JsonProperty("status")
    public boolean status;
    @JsonProperty("message")
    public String message;

}
