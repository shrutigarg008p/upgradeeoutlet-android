
package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "position",
    "category_id"
})
public class UpgradedCategoryLink {

    @JsonProperty("position")
    public Integer position;
    @JsonProperty("category_id")
    public String categoryId;

}
