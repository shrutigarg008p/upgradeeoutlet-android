
package com.eoutlet.Eoutlet.pojo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "filter_groups",
        "page_size"
})
public class SearchCriteria {

    @JsonProperty("filter_groups")
    public List<FilterGroup> filterGroups = null;
    @JsonProperty("page_size")
    public int page_size;
}
