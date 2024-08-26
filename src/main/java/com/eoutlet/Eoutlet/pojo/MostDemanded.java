package com.eoutlet.Eoutlet.pojo;

import com.eoutlet.Eoutlet.api.request.ListItems;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "arr"
})
public class MostDemanded implements Serializable {
    @JsonProperty("arr")
    public List<ListItems> arr = null;
    @JsonProperty("method")
    public Object method;





}
