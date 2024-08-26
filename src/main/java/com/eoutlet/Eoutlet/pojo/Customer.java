
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
    "group_id",
    "created_at",
    "updated_at",
    "created_in",
    "email",
    "firstname",
    "lastname",
    "store_id",
    "website_id",
    "addresses",
    "disable_auto_group_change",
    "extension_attributes",
    "custom_attributes"
})
public class Customer {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("group_id")
    public Integer groupId;
    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("updated_at")
    public String updatedAt;
    @JsonProperty("created_in")
    public String createdIn;
    @JsonProperty("email")
    public String email;
    @JsonProperty("firstname")
    public String firstname;
    @JsonProperty("lastname")
    public String lastname;
    @JsonProperty("store_id")
    public Integer storeId;
    @JsonProperty("website_id")
    public Integer websiteId;
    @JsonProperty("addresses")
    public List<Object> addresses = null;
    @JsonProperty("disable_auto_group_change")
    public Integer disableAutoGroupChange;
    @JsonProperty("extension_attributes")
    public CartExtensionAttributes extensionAttributes;
    @JsonProperty("custom_attributes")
    public List<CustomAttribute> customAttributes = null;

}
