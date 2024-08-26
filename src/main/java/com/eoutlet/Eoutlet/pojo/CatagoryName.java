package com.eoutlet.Eoutlet.pojo;



import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;



@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "data",
        "image",
        "id",
        "caption"
})
public class CatagoryName {

    @JsonProperty("name")
    public String name;
    @JsonProperty("image")
    public String image;
    @JsonProperty("id")
    public String id;
    @JsonProperty("caption")
    public String caption;
    @JsonProperty("data")
    public List<CatagoryList> data = null;

}