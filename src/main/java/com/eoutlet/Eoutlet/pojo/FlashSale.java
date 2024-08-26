
package com.eoutlet.Eoutlet.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "id",
    "sku",
    "price",
    "old_price",
    "image",
    "created",
    "category_name"
})

public class FlashSale {

    @JsonProperty("name")
    public String name;
    @JsonProperty("id")
    public String id;
    @JsonProperty("sku")
    public String sku;
    @JsonProperty("price")
    public Integer price;
    @JsonProperty("old_price")
    public Integer oldPrice;
    @JsonProperty("image")
    public String image;
    @JsonProperty("created")
    public String created;
    @JsonProperty("category_name")
    public String categoryName;

}
