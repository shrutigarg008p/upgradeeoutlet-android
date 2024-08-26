
package com.eoutlet.Eoutlet.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "items",
    "subtotal",
    "grandtotal",
    "gift_wrap_fee",
        "cart_id",
})
public class UpgradedCartData {

    @JsonProperty("items")
    public List<UpgradedItem> items = null;
    @JsonProperty("subtotal")
    public String subtotal;
    @JsonProperty("grandtotal")
    public String grandtotal;
    @JsonProperty("gift_wrap_fee")
    public String gift_wrap_fee;
    @JsonProperty("cart_id")
    public String cart_id;


}

