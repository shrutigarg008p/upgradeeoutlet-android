
package com.eoutlet.Eoutlet.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "msg",
    "data",
    "order_id",
    "payment_method",
    "is_partial",
       "redirect_link"
})
public class CheckoutOrderResponse {

    @JsonProperty("msg")
    public String msg;
    @JsonProperty("data")
    public List<Object> data = null;
    @JsonProperty("order_id")
    public String orderId;
    @JsonProperty("payment_method")
    public String paymentMethod;
    @JsonProperty("is_partial")
    public Object isPartial;
    @JsonProperty("redirect_link")
    public String redirect_link;
}
