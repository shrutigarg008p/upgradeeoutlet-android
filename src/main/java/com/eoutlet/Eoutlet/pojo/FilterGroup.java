
package com.eoutlet.Eoutlet.pojo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "filters"
})
public class FilterGroup {

    @JsonProperty("filters")
    public List<Filter> filters = null;

}
