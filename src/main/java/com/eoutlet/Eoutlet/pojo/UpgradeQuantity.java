
package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "item_id",
    "sku",
    "qty",
    "name",
    "price",
    "product_type",
    "quote_id",
        "message",
        "trace"
})
public class UpgradeQuantity {

    @JsonProperty("item_id")
    public Integer itemId;
    @JsonProperty("sku")
    public String sku;
    @JsonProperty("qty")
    public Integer qty;
    @JsonProperty("name")
    public String name;
    @JsonProperty("price")
    public Integer price;
    @JsonProperty("product_type")
    public String productType;
    @JsonProperty("quote_id")
    public String quoteId;
    @JsonProperty("message")
    public String message;
    @JsonProperty("trace")
    public String trace;
}
