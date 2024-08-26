
package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "field",
    "direction"
})
public class SortOrder {

    @JsonProperty("field")
    public Object field;
    @JsonProperty("direction")
    public Object direction;

}
