
package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "wrap_id",
    "name",
    "status",
    "price_type",
    "amount",
    "description",
    "image",
    "category",
    "sort_order",
    "created_at",
    "updated_at"
})
public class ItemGift {

    @JsonProperty("wrap_id")
    public Integer wrapId;
    @JsonProperty("name")
    public String name;
    @JsonProperty("status")
    public Integer status;
    @JsonProperty("price_type")
    public Integer priceType;
    @JsonProperty("amount")
    public Integer amount;
    @JsonProperty("description")
    public Object description;
    @JsonProperty("image")
    public String image;
    @JsonProperty("category")
    public String category;
    @JsonProperty("sort_order")
    public Integer sortOrder;
    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("updated_at")
    public String updatedAt;

}
