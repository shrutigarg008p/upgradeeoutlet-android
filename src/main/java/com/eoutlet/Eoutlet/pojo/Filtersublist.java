
package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "display",
    "value",
    "count"

})
public class Filtersublist {

    @JsonProperty("display")
    public String display;

    @JsonProperty("value")
    public String value;
    @JsonProperty("count")
    public Integer count;

}
