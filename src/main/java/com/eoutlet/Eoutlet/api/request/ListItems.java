
package com.eoutlet.Eoutlet.api.request;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;



@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "name",
        "id",
        "sku",
        "price",
        "old_price",
        "img",
        "short_descrition",
        "created",
        "size",
        "color",
        "color_name",
        "category_name",
        "size_chart",
        "iswishlist"
})
public class ListItems implements Serializable {

/*
"name":"تيشيرت روبرتو كفالى","id":"32972","sku":"rc01196","price":746,"old_price":0,"img":"https:\/\/upgrade\/pub\/media\/catalog\/product\/imp\/ort\/3-3-33_1__2.png","created":"2020-11-26 14:41:33","category_name":"رجال"
*/

    @JsonProperty("name")
    public String name;
    @JsonProperty("id")
    public String id;
    @JsonProperty("sku")
    public String sku;
    @JsonProperty("price")
    public String price;
    @JsonProperty("old_price")
    public String oldPrice;
    @JsonProperty("img")
    public String img;
    @JsonProperty("short_descrition")
    public String short_descrition;
    @JsonProperty("type")
    public String type;
    @JsonProperty("created")
    public String created;
    @JsonProperty("size")
    public String size;
    @JsonProperty("color")
    public String color;
    @JsonProperty("color_name")
    public String color_name;


    @JsonProperty("category_name")
    public String category_name;
    @JsonProperty("size_chart")
    public String size_chart;
    @JsonProperty("iswishlist")
    public boolean iswishlist = false;

}