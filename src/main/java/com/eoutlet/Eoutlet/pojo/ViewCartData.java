package com.eoutlet.Eoutlet.pojo;
import com.eoutlet.Eoutlet.api.request.ListItems;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "id",
        "sku",
        "img",
        "qty",
        "price",
        "old_price",
        "product_id",
        "option",

        "instock",

        "product_data"
})
public class ViewCartData implements Serializable {

    @JsonProperty("name")
    public String name;
    @JsonProperty("type")
    public String type;
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
    @JsonProperty("price")
    public String price;



    @JsonProperty("instock")
    public Integer instock;

    @JsonProperty("old_price")
    public Integer old_price;


    @JsonProperty("option")
    public  List<Option> option = null;
    @JsonProperty("product_data")
    public ListItems productData;





}
