
package com.eoutlet.Eoutlet.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({

   "type",
    "product_id",
    "sku",
    "name",
    "old_price",
    "price",
    "image",
    "description",
    "shortdescription",
    "stock",
       " seller_name",
        "seller_id",
        "size_chart",
        "size",
        "color",
        "color_name",
        "category_name",
      "category_id",
        "size_chart_html"
        ,"is_size_chart"
})
public class Data {
    @JsonProperty("type")
    public String type;
    @JsonProperty("product_id")
    public String productId;
    @JsonProperty("sku")
    public String sku;
    @JsonProperty("name")
    public String name;
    @JsonProperty("old_price")
    public Integer oldPrice;
    @JsonProperty("price")
    public Integer price;
    @JsonProperty("image")
    public List<String> image = null;
    @JsonProperty("description")
    public String description;
    @JsonProperty("shortdescription")
    public String shortdescription;
    @JsonProperty("stock")
    public Boolean stock;
    @JsonProperty("seller_name")
    public String seller_name;

    @JsonProperty("seller_id")
    public String seller_id;

    @JsonProperty("size_chart")
    public String size_chart = " ";

    @JsonProperty("size")
    public Object size;

    @JsonProperty("color")
    public String color;

    @JsonProperty("color_name")
    public String color_name;
    @JsonProperty("category_id")
    public String category_id;
    @JsonProperty( "category_name")
    public String category_name;
    @JsonProperty( "size_chart_html")
    public String size_chart_html;
    @JsonProperty( "is_size_chart")
    public String is_size_chart;
}
