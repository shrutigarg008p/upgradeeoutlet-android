package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "msg",
        "data",
        "order_id"
})
public class
StcOrderResponse {
    @JsonProperty("msg")
    public String msg;


    /*@JsonProperty("data")
    public List<Object> data = null;*/


    @JsonProperty("data")
    public Object data = null;
    @JsonProperty("order_id")
    public String orderId;
}
