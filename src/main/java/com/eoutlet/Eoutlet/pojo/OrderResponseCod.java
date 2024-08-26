
package com.eoutlet.Eoutlet.pojo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "msg",
        "data",
        "order_id",
        "payment_method",
        "is_partial"
})
public class OrderResponseCod {

    @JsonProperty("msg")
    public String msg;
    @JsonProperty("data")
    public List<Object> data = null;
    @JsonProperty("order_id")
    public String orderId;
    @JsonProperty("payment_method")
    public String paymentMethod;
    @JsonProperty("is_partial")
    //public Boolean isPartial;
    public Object isPartial;
}
