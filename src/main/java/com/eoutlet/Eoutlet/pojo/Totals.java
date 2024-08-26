
package com.eoutlet.Eoutlet.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "grand_total",
    "base_grand_total",
    "subtotal",
    "base_subtotal",
    "discount_amount",
    "base_discount_amount",
    "subtotal_with_discount",
    "base_subtotal_with_discount",
    "shipping_amount",
    "base_shipping_amount",
    "shipping_discount_amount",
    "base_shipping_discount_amount",
    "tax_amount",
    "base_tax_amount",
    "weee_tax_applied_amount",
    "shipping_tax_amount",
    "base_shipping_tax_amount",
    "subtotal_incl_tax",
    "shipping_incl_tax",
    "base_shipping_incl_tax",
    "base_currency_code",
    "quote_currency_code",
    "items_qty",
    "items",
    "total_segments",
        "coupon_code",
    "extension_attributes"
})
public class Totals {

    @JsonProperty("grand_total")
    public Float grandTotal;
    @JsonProperty("base_grand_total")
    public Float baseGrandTotal;
    @JsonProperty("subtotal")
    public Float subtotal;
    @JsonProperty("base_subtotal")
    public Integer baseSubtotal;
    @JsonProperty("discount_amount")
    public Integer discountAmount;
    @JsonProperty("base_discount_amount")
    public Integer baseDiscountAmount;
    @JsonProperty("subtotal_with_discount")
    public Float subtotalWithDiscount;
    @JsonProperty("base_subtotal_with_discount")
    public Integer baseSubtotalWithDiscount;
    @JsonProperty("shipping_amount")
    public Integer shippingAmount;
    @JsonProperty("base_shipping_amount")
    public Integer baseShippingAmount;
    @JsonProperty("shipping_discount_amount")
    public Integer shippingDiscountAmount;
    @JsonProperty("base_shipping_discount_amount")
    public Integer baseShippingDiscountAmount;
    @JsonProperty("tax_amount")
    public Integer taxAmount;
    @JsonProperty("base_tax_amount")
    public Integer baseTaxAmount;
    @JsonProperty("weee_tax_applied_amount")
    public Object weeeTaxAppliedAmount;
    @JsonProperty("shipping_tax_amount")
    public Integer shippingTaxAmount;
    @JsonProperty("base_shipping_tax_amount")
    public Integer baseShippingTaxAmount;
    @JsonProperty("subtotal_incl_tax")
    public Float subtotalInclTax;
    @JsonProperty("shipping_incl_tax")
    public Integer shippingInclTax;
    @JsonProperty("base_shipping_incl_tax")
    public Integer baseShippingInclTax;
    @JsonProperty("base_currency_code")
    public String baseCurrencyCode;
    @JsonProperty("quote_currency_code")
    public String quoteCurrencyCode;
    @JsonProperty("items_qty")
    public Integer itemsQty;
    @JsonProperty("coupon_code")
    public String coupon_code;
    @JsonProperty("items")
    public List<ItemPaymentmethod> items = null;
    @JsonProperty("total_segments")
    public List<TotalSegment> totalSegments = null;
    @JsonProperty("extension_attributes")
    public ExtensionAttributes_2 extensionAttributes;

}
