
package com.eoutlet.Eoutlet.pojo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "filter_groups",
    "sort_orders"
})
public class SearchCriteriaGift {

    @JsonProperty("filter_groups")
    public List<Object> filterGroups = null;
    @JsonProperty("sort_orders")
    public List<SortOrder> sortOrders = null;

}
