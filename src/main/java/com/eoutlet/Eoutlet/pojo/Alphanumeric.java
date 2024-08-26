package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "alphabetic",
        "data"
})
public class Alphanumeric {


    @JsonProperty("alphabetic")
    public String alphabetic;
    @JsonProperty("data")
    public List<BrandNameDetail> data = null;
}
