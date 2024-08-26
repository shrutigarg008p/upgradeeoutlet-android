package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "msg",
        "data",
        "shipping_title",
        "shipping",
        "shipping_method",
        "shipping_tax"
})
public class GetShippingCharge {


    @JsonProperty("msg")
    public String msg;
    @JsonProperty("data")
    public List<Object> data = null;
    @JsonProperty("shipping_title")
    public String shipping_title;
    @JsonProperty("shipping")
    public Integer shipping;
    @JsonProperty("shipping_method")
    public String shipping_method;
    @JsonProperty("shipping_tax")
    public int shipping_tax;
}
