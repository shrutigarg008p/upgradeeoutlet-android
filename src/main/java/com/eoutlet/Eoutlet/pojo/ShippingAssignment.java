
package com.eoutlet.Eoutlet.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "shipping",
    "items"
})
public class ShippingAssignment {

    @JsonProperty("shipping")
    public Shipping shipping;
    @JsonProperty("items")
    public List<Item_> items = null;

}
