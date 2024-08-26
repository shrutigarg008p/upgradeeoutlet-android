package com.eoutlet.Eoutlet.pojo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "value_index",
        "name",
        "image",

        "salable",
        "price",
        "old_price"

})
public class colorandsizedetail2 {


    @JsonProperty("value_index")
    public String value_Index;
    @JsonProperty("name")
    public String name;
    @JsonProperty("image")
    public String image;
    @JsonProperty("salable")
    public int salable;
    @JsonProperty("price")
    public int price;
    @JsonProperty("old_price")
    public int old_price;

}
