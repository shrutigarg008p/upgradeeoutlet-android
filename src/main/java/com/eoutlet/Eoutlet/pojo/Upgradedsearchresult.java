
package com.eoutlet.Eoutlet.pojo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "items",
    "search_criteria",
    "total_count"
})
public class Upgradedsearchresult {

    @JsonProperty("items")
    public List<UpgradedSearchItem> items = null;
    @JsonProperty("search_criteria")
    public SearchCriteria searchCriteria;
    @JsonProperty("total_count")
    public Integer totalCount;

}
