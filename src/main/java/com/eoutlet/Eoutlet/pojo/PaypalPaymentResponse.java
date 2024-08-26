
package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "msg",
    "redirect_link",
    "order_id",
    "payment_id"
})
public class PaypalPaymentResponse {

    @JsonProperty("msg")
    public String msg;
    @JsonProperty("redirect_link")
    public String redirectLink;
    @JsonProperty("order_id")
    public String orderId;
    @JsonProperty("payment_id")
    public String paymentId;

}
