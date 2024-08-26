
package com.eoutlet.Eoutlet.pojo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "website_ids",
    "category_links"
})
public class UpgradedExtensionAttributes {

    @JsonProperty("website_ids")
    public List<Integer> websiteIds = null;
    @JsonProperty("category_links")
    public List<UpgradedCategoryLink> categoryLinks = null;

}
