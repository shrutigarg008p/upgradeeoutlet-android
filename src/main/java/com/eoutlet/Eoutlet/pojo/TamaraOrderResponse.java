
package com.eoutlet.Eoutlet.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "msg",
    "order_id"
})

public class TamaraOrderResponse {

    @JsonProperty("msg")
    public String msg;
    @JsonProperty("order_id")
    public String orderId;

}
