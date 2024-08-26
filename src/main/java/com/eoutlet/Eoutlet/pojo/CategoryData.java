
package com.eoutlet.Eoutlet.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "promotionals",
    "subcategory",
        "brands",
       "brand_banner"
})

public class CategoryData{

    @JsonProperty("id")
    public String id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("promotionals")
    public List<UpgradePromotional> promotionals = null;
    @JsonProperty("subcategory")
    public List<UpgradeSubcategory> subcategory = null;
    @JsonProperty("brands")
    public List<BrandsDetail> brands = null;
    @JsonProperty("brand_banner")
    public List< BrandBanner>brands_banner = null;
}
