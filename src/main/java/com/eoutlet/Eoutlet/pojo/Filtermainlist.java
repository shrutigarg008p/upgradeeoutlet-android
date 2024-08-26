
package com.eoutlet.Eoutlet.pojo;

import java.io.Serializable;
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
        "code"
})
public class Filtermainlist implements Serializable {

    @JsonProperty("name")
    public String name;
    @JsonProperty("data")
    public List<Filtersublist> data = null;

    @JsonProperty("code")
    public String code;


}
