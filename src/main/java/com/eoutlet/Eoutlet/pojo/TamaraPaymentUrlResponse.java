
package com.eoutlet.Eoutlet.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "order_id",
    "checkout_id",
    "checkout_url",
        "message",
})

public class TamaraPaymentUrlResponse {

    @JsonProperty("order_id")
    public String orderId;
    @JsonProperty("checkout_id")
    public String checkoutId;
    @JsonProperty("checkout_url")
    public String checkoutUrl;
    @JsonProperty("message")
    public String message;

}
