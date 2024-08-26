package com.eoutlet.Eoutlet.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "website_ids",
        "configurable_product_options",
        "configurable_product_links"
})
public class ExtensionAttributes {

    @JsonProperty("website_ids")
    public List<Integer> websiteIds = null;
    @JsonProperty("configurable_product_options")
    public List<ConfigurableProductOption> configurableProductOptions = null;
    @JsonProperty("configurable_product_links")
    public List<Integer> configurableProductLinks = null;

}