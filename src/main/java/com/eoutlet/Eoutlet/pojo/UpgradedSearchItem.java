
package com.eoutlet.Eoutlet.pojo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "sku",
    "name",
    "attribute_set_id",
    "price",
    "status",
    "visibility",
    "type_id",
    "created_at",
    "updated_at",
    "weight",
    "extension_attributes",
    "product_links",
    "options",
    "media_gallery_entries",
    "tier_prices",
    "custom_attributes"
})
public class UpgradedSearchItem implements Serializable {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("sku")
    public String sku;
    @JsonProperty("name")
    public String name;
    @JsonProperty("attribute_set_id")
    public Integer attributeSetId;
    @JsonProperty("price")
    public Integer price;
    @JsonProperty("status")
    public Integer status;
    @JsonProperty("visibility")
    public Integer visibility;
    @JsonProperty("type_id")
    public String typeId;
    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("updated_at")
    public String updatedAt;
    @JsonProperty("weight")
    public Float weight;

    @JsonProperty("extension_attributes")
    public UpgradedExtensionAttributes extensionAttributes;

    @JsonProperty("product_links")
    public List<Object> productLinks = null;
    @JsonProperty("options")
    public List<Object> options = null;
    @JsonProperty("media_gallery_entries")
    public List<UpgradedMediaGalleryEntry> mediaGalleryEntries = null;
    @JsonProperty("tier_prices")
    public List<Object> tierPrices = null;

   /* @JsonProperty("custom_attributes")
    public List<Object> customAttributes = null;*/

    @JsonProperty("custom_attributes")
    public List<UpgradedCustomAttribute> customAttributes = null;

}
