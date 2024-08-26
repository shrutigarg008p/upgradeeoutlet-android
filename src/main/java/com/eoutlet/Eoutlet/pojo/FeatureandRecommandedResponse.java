
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
    "msg",
    "featured",
    "recommendedlist"
})
public class FeatureandRecommandedResponse {




    @JsonProperty("msg")
    public String msg;
    @JsonProperty("featured")
    public List<Featured> featured = null;
    @JsonProperty("recommendedlist")
    public List<Recommendedlist> recommendedlist = null;
/*
    @JsonProperty("msg")
    public String msg;
    @JsonProperty("featured")
    public List<ListItems> featured = null;
    @JsonProperty("recommendedlist")
    public List<ListItems> recommendedlist = null;*/

}
