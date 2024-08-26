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
        "created_at",
        "updated_at",
        "is_active",
        "is_virtual",
        "items",
        "items_count",
        "items_qty",
        "customer",
        "billing_address",
        "orig_order_id",
        "currency",
        "customer_is_guest",
        "customer_note_notify",
        "customer_tax_class_id",
        "store_id",
        "extension_attributes"
})
public class ResponseFields {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("updated_at")
    public String updatedAt;
    @JsonProperty("is_active")
    public Boolean isActive;
    @JsonProperty("is_virtual")
    public Boolean isVirtual;
    @JsonProperty("items")
    public List<Object> items = null;
    @JsonProperty("items_count")
    public Integer itemsCount;
    @JsonProperty("items_qty")
    public Integer itemsQty;
    @JsonProperty("customer")
    public Customer customer;
    @JsonProperty("billing_address")
    public BillingAddress billingAddress;
    @JsonProperty("orig_order_id")
    public Integer origOrderId;
    @JsonProperty("currency")
    public Currency currency;
    @JsonProperty("customer_is_guest")
    public Boolean customerIsGuest;
    @JsonProperty("customer_note_notify")
    public Boolean customerNoteNotify;
    @JsonProperty("customer_tax_class_id")
    public Integer customerTaxClassId;
    @JsonProperty("store_id")
    public Integer storeId;
    @JsonProperty("extension_attributes")
    public ExtensionAttributes_ extensionAttributes;

}
