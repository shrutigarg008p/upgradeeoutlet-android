
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
        "data"
})
public class UpgardeCheckoutOrderResponse {

    @JsonProperty("msg")
    public String msg;
    @JsonProperty("redirect_link")
    public String redirect_link;
    @JsonProperty("order_id")
    public String orderId;
    @JsonProperty("data")
    public String data;
}
