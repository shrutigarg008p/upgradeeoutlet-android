package com.eoutlet.Eoutlet.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "message",
        "data"
})
public class DeleteCustomerAccount {

    @JsonProperty("message")
    public String message;
    @JsonProperty("data")
    public String data;
}
