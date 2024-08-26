package com.eoutlet.Eoutlet.api.request;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class CatagoryList {




    @JsonProperty("msg")
    public String msg;

    @JsonProperty("total")
    public String page;

    @JsonProperty("response")
    public String response;
    @JsonProperty("message")
    public String message;
    @JsonProperty("product_count")
    public String product_count;

    @JsonProperty("data")
    public List<ListItems> data = null;




}
