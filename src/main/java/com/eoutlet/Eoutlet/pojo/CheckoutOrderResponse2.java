
package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

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
public class CheckoutOrderResponse2 {

    @JsonProperty("msg")
    public String msg;
    @JsonProperty("data")
    public List<Object> data = null;
    @JsonProperty("order_id")
    public String orderId;
    @JsonProperty("payment_method")
    public String paymentMethod;
    @JsonProperty("is_partial")
    public String isPartial;
    @JsonProperty("redirect_link")
    public String redirect_link;
}
