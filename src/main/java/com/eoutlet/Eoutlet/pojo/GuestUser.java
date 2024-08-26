package com.eoutlet.Eoutlet.pojo;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "msg",
        "mask_key",
        "quote_id"
})
public class GuestUser {

    @JsonProperty("msg")
    public String msg;
    @JsonProperty("mask_key")
    public String maskKey;
    @JsonProperty("quote_id")
    public Integer quoteId;

}