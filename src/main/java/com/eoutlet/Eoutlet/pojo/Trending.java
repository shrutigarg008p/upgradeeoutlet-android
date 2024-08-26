package com.eoutlet.Eoutlet.pojo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "msg",
        "data",
        "couponbanner"
})
public class Trending {

    @JsonProperty("msg")
    public String msg;
    @JsonProperty("data")
    public List<Datum2> data = null;
    @JsonProperty("couponbanner")
    public String couponbanner;

}