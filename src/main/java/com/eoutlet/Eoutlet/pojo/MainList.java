package com.eoutlet.Eoutlet.pojo;

import java.util.List;

import com.eoutlet.Eoutlet.api.request.ListItems;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "cate",
        "data"
})
public class MainList {

    @JsonProperty("cate")
    public String cate;
    @JsonProperty("data")
    public List<ListItems> data = null;




}
