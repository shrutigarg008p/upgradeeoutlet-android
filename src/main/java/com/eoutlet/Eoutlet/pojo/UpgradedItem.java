
package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "price",
        "old_price",
    "id",
    "product_id",
    "sku",
    "img",
    "qty",
    "instock",
        "category_id",
        "category_name",
        "brand_name"

})
public class UpgradedItem {

    @JsonProperty("name")
    public String name;
    @JsonProperty("price")
    public String price;
    @JsonProperty("old_price")
    public String old_price;
    @JsonProperty("id")
    public String id;
    @JsonProperty("product_id")
    public String productId;
    @JsonProperty("sku")
    public String sku;
    @JsonProperty("img")
    public String img;
    @JsonProperty("qty")
    public Object qty;
    @JsonProperty("instock")
    public Integer instock;
    @JsonProperty("optionss")
    public Optionss optionss;

    @JsonProperty("category_id")
    public String category_id;
    @JsonProperty("category_name")
    public String category_name;
    @JsonProperty("brand_name")
    public String brand_name;
}
