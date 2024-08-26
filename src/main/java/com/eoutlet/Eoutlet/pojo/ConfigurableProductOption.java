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
        "attribute_id",
        "label",
        "position",
        "values",
        "product_id"
})
public class ConfigurableProductOption {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("attribute_id")
    public String attributeId;
    @JsonProperty("label")
    public String label;
    @JsonProperty("position")
    public Integer position;
    @JsonProperty("values")
    public List<Value> values = null;
    @JsonProperty("product_id")
    public Integer productId;

}