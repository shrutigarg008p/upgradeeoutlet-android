package com.eoutlet.Eoutlet.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "value",
        "label"
})
public class Size implements Serializable {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("value")
    public Object value;
    @JsonProperty("label")
    public Object label;
}
