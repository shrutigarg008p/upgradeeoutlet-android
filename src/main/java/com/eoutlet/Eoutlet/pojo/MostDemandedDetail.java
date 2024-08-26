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
        "img",
        "short_descrition"
})
public class MostDemandedDetail {


    @JsonProperty("name")
    public String name;
    @JsonProperty("id")

    public String id;
    @JsonProperty("short_descritionn")
    public String short_descrition;
    @JsonProperty("sku")
    public String sku;
    @JsonProperty("price")
    public String price;
    @JsonProperty("old_price")
    public String oldPrice;
    @JsonProperty("img")
    public String img;






}
