
package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({


        "img",
        "id",
        "name",
        "caption",
        "children"

    /*"type",
    "name",
    "short_descrition",
    "id",
    "sku",
    "price",
    "old_price",
    "img",
    "size",
    "color",
    "color_name"*/
})
public class Recommendedlist implements Serializable {
/*
    @JsonProperty("type")
    public String type;
    @JsonProperty("name")
    public String name;
    @JsonProperty("short_descrition")
    public String shortDescrition;
    @JsonProperty("id")
    public String id;
    @JsonProperty("sku")
    public String sku;
    @JsonProperty("price")
    public Integer price;
    @JsonProperty("old_price")
    public Integer oldPrice;
    @JsonProperty("img")
    public String img;
    @JsonProperty("size")
    public String size;
    @JsonProperty("color")
    public String color;
    @JsonProperty("color_name")
    public String colorName;*/
@JsonProperty("img")
public String img;
    @JsonProperty("id")
    public String id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("caption")
    public String caption;
    @JsonProperty("children")
    public List<Child> children = null;

}
