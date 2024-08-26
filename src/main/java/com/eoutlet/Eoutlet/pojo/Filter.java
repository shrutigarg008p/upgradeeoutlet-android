
package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "field",
    "value",
    "condition_type"
})
public class Filter {

    @JsonProperty("field")
    public String field;
    @JsonProperty("value")
    public String value;
    @JsonProperty("condition_type")
    public String conditionType;

}
