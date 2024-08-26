
package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "item_id",
    "price",
    "base_price",
    "qty",
    "row_total",
    "base_row_total",
    "row_total_with_discount",
    "tax_amount",
    "base_tax_amount",
    "tax_percent",
    "discount_amount",
    "base_discount_amount",
    "discount_percent",
    "price_incl_tax",
    "base_price_incl_tax",
    "row_total_incl_tax",
    "base_row_total_incl_tax",
    "options",
    "weee_tax_applied_amount",
    "weee_tax_applied",
    "name"
})
public class ItemPaymentmethod {

    @JsonProperty("item_id")
    public Integer itemId;
    @JsonProperty("price")
    public Integer price;
    @JsonProperty("base_price")
    public Integer basePrice;
    @JsonProperty("qty")
    public Integer qty;
    @JsonProperty("row_total")
    public Float rowTotal;
    @JsonProperty("base_row_total")
    public Integer baseRowTotal;
    @JsonProperty("row_total_with_discount")
    public Integer rowTotalWithDiscount;
    @JsonProperty("tax_amount")
    public Integer taxAmount;
    @JsonProperty("base_tax_amount")
    public Integer baseTaxAmount;
    @JsonProperty("tax_percent")
    public Integer taxPercent;
    @JsonProperty("discount_amount")
    public Integer discountAmount;
    @JsonProperty("base_discount_amount")
    public Integer baseDiscountAmount;
    @JsonProperty("discount_percent")
    public Integer discountPercent;
    @JsonProperty("price_incl_tax")
    public Float priceInclTax;
    @JsonProperty("base_price_incl_tax")
    public Integer basePriceInclTax;
    @JsonProperty("row_total_incl_tax")
    public Float rowTotalInclTax;
    @JsonProperty("base_row_total_incl_tax")
    public Integer baseRowTotalInclTax;
    @JsonProperty("options")
    public String options;
    @JsonProperty("weee_tax_applied_amount")
    public Object weeeTaxAppliedAmount;
    @JsonProperty("weee_tax_applied")
    public Object weeeTaxApplied;
    @JsonProperty("name")
    public String name;

}
