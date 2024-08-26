package com.eoutlet.Eoutlet.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "base_currency_code",
        "base_discount_amount",
        "base_grand_total",
        "base_discount_tax_compensation_amount",
        "base_shipping_amount",
        "base_shipping_discount_amount",
        "base_shipping_discount_tax_compensation_amnt",
        "base_shipping_incl_tax",
        "base_shipping_tax_amount",
        "base_subtotal",
        "base_subtotal_incl_tax",
        "base_tax_amount",
        "base_total_due",
        "base_to_global_rate",
        "base_to_order_rate",
        "billing_address_id",
        "created_at",
        "customer_email",
        "customer_firstname",
        "customer_gender",
        "customer_group_id",
        "customer_id",
        "customer_is_guest",
        "customer_lastname",
        "customer_note_notify",
        "discount_amount",
        "email_sent",
        "entity_id",
        "global_currency_code",
        "grand_total",
        "discount_tax_compensation_amount",
        "increment_id",
        "is_virtual",
        "order_currency_code",
        "protect_code",
        "quote_id",
        "remote_ip",
        "shipping_amount",
        "shipping_description",
        "shipping_discount_amount",
        "shipping_discount_tax_compensation_amount",
        "shipping_incl_tax",
        "shipping_tax_amount",
        "state",
        "status",
        "store_currency_code",
        "store_id",
        "store_name",
        "store_to_base_rate",
        "store_to_order_rate",
        "subtotal",
        "subtotal_incl_tax",
        "tax_amount",
        "total_due",
        "total_item_count",
        "total_qty_ordered",
        "updated_at",
        "weight",
        "items",
        "billing_address",
        "payment",
        "status_histories",
        "extension_attributes"
})
public class GetOrderdetail {

    @JsonProperty("base_currency_code")
    public String baseCurrencyCode;
    @JsonProperty("base_discount_amount")
    public Integer baseDiscountAmount;
    @JsonProperty("base_grand_total")
    public Float baseGrandTotal;
    @JsonProperty("base_discount_tax_compensation_amount")
    public Integer baseDiscountTaxCompensationAmount;
    @JsonProperty("base_shipping_amount")
    public Integer baseShippingAmount;
    @JsonProperty("base_shipping_discount_amount")
    public Integer baseShippingDiscountAmount;
    @JsonProperty("base_shipping_discount_tax_compensation_amnt")
    public Integer baseShippingDiscountTaxCompensationAmnt;
    @JsonProperty("base_shipping_incl_tax")
    public Integer baseShippingInclTax;
    @JsonProperty("base_shipping_tax_amount")
    public Integer baseShippingTaxAmount;
    @JsonProperty("base_subtotal")
    public Integer baseSubtotal;
    @JsonProperty("base_subtotal_incl_tax")
    public Integer baseSubtotalInclTax;
    @JsonProperty("base_tax_amount")
    public Integer baseTaxAmount;
    @JsonProperty("base_total_due")
    public Float baseTotalDue;
    @JsonProperty("base_to_global_rate")
    public Integer baseToGlobalRate;
    @JsonProperty("base_to_order_rate")
    public Float baseToOrderRate;
    @JsonProperty("billing_address_id")
    public Integer billingAddressId;
    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("customer_email")
    public String customerEmail;
    @JsonProperty("customer_firstname")
    public String customerFirstname;
    @JsonProperty("customer_gender")
    public Integer customerGender;
    @JsonProperty("customer_group_id")
    public Integer customerGroupId;
    @JsonProperty("customer_id")
    public Integer customerId;
    @JsonProperty("customer_is_guest")
    public Integer customerIsGuest;
    @JsonProperty("customer_lastname")
    public String customerLastname;
    @JsonProperty("customer_note_notify")
    public Integer customerNoteNotify;
    @JsonProperty("discount_amount")
    public Integer discountAmount;
    @JsonProperty("email_sent")
    public Integer emailSent;
    @JsonProperty("entity_id")
    public Integer entityId;
    @JsonProperty("global_currency_code")
    public String globalCurrencyCode;
    @JsonProperty("grand_total")
    public Float grandTotal;
    @JsonProperty("discount_tax_compensation_amount")
    public Integer discountTaxCompensationAmount;
    @JsonProperty("increment_id")
    public String incrementId;
    @JsonProperty("is_virtual")
    public Integer isVirtual;
    @JsonProperty("order_currency_code")
    public String orderCurrencyCode;
    @JsonProperty("protect_code")
    public String protectCode;
    @JsonProperty("quote_id")
    public Integer quoteId;
    @JsonProperty("remote_ip")
    public String remoteIp;
    @JsonProperty("shipping_amount")
    public Integer shippingAmount;
    @JsonProperty("shipping_description")
    public String shippingDescription;
    @JsonProperty("shipping_discount_amount")
    public Integer shippingDiscountAmount;
    @JsonProperty("shipping_discount_tax_compensation_amount")
    public Integer shippingDiscountTaxCompensationAmount;
    @JsonProperty("shipping_incl_tax")
    public Integer shippingInclTax;
    @JsonProperty("shipping_tax_amount")
    public Integer shippingTaxAmount;
    @JsonProperty("state")
    public String state;
    @JsonProperty("status")
    public String status;
    @JsonProperty("store_currency_code")
    public String storeCurrencyCode;
    @JsonProperty("store_id")
    public Integer storeId;
    @JsonProperty("store_name")
    public String storeName;
    @JsonProperty("store_to_base_rate")
    public Integer storeToBaseRate;
    @JsonProperty("store_to_order_rate")
    public Integer storeToOrderRate;
    @JsonProperty("subtotal")
    public Float subtotal;
    @JsonProperty("subtotal_incl_tax")
    public Float subtotalInclTax;
    @JsonProperty("tax_amount")
    public Integer taxAmount;
    @JsonProperty("total_due")
    public Float totalDue;
    @JsonProperty("total_item_count")
    public Integer totalItemCount;
    @JsonProperty("total_qty_ordered")
    public Integer totalQtyOrdered;
    @JsonProperty("updated_at")
    public String updatedAt;
    @JsonProperty("weight")
    public Float weight;
    @JsonProperty("items")
    public List<Item> items = null;
    @JsonProperty("billing_address")
    public Object billingAddress;
    @JsonProperty("payment")
    public Object payment;
    @JsonProperty("status_histories")
    public List<Object> statusHistories = null;
    @JsonProperty("extension_attributes")
    public Object extensionAttributes;


    /*{
    "base_currency_code": "USD",
    "base_discount_amount": 0,
    "base_grand_total": 804.33,
    "base_discount_tax_compensation_amount": 0,
    "base_shipping_amount": 0,
    "base_shipping_discount_amount": 0,
    "base_shipping_discount_tax_compensation_amnt": 0,
    "base_shipping_incl_tax": 0,
    "base_shipping_tax_amount": 0,
    "base_subtotal": 799,
    "base_subtotal_incl_tax": 799,
    "base_tax_amount": 0,
    "base_total_due": 804.33,
    "base_to_global_rate": 1,
    "base_to_order_rate": 3.75,
    "billing_address_id": 665,
    "created_at": "2021-03-25 06:25:32",
    "customer_email": "arpan@unyscape.com",
    "customer_firstname": "arpan test account",
    "customer_gender": 1,
    "customer_group_id": 1,
    "customer_id": 74060,
    "customer_is_guest": 0,
    "customer_lastname": "bhatia",
    "customer_note_notify": 1,
    "discount_amount": 0,
    "email_sent": 1,
    "entity_id": 361,
    "global_currency_code": "USD",
    "grand_total": 3001.58,
    "discount_tax_compensation_amount": 0,
    "increment_id": "2000000189",
    "is_virtual": 0,
    "order_currency_code": "SAR",
    "protect_code": "9af27c7e3e0c846f4a63f852d176c724",
    "quote_id": 2479,
    "remote_ip": "49.36.165.254",
    "shipping_amount": 0,
    "shipping_description": "Free Shipping - Free",
    "shipping_discount_amount": 0,
    "shipping_discount_tax_compensation_amount": 0,
    "shipping_incl_tax": 0,
    "shipping_tax_amount": 0,
    "state": "new",
    "status": "pending",
    "store_currency_code": "USD",
    "store_id": 2,
    "store_name": "Main Website\nMain Website Store\nArabic",
    "store_to_base_rate": 0,
    "store_to_order_rate": 0,
    "subtotal": 2996.25,
    "subtotal_incl_tax": 2996.25,
    "tax_amount": 0,
    "total_due": 3001.58,
    "total_item_count": 1,
    "total_qty_ordered": 1,
    "updated_at": "2021-03-25 06:25:35",
    "weight": 0.0005,
    "items": [
        {
            "amount_refunded": 0,
            "base_amount_refunded": 0,
            "base_discount_amount": 0,
            "base_discount_invoiced": 0,
            "base_discount_tax_compensation_amount": 0,
            "base_original_price": 1598,
            "base_price": 799,
            "base_price_incl_tax": 799,
            "base_row_invoiced": 0,
            "base_row_total": 799,
            "base_row_total_incl_tax": 799,
            "base_tax_amount": 0,
            "base_tax_invoiced": 0,
            "created_at": "2021-03-25 06:25:33",
            "discount_amount": 0,
            "discount_invoiced": 0,
            "discount_percent": 0,
            "free_shipping": 0,
            "discount_tax_compensation_amount": 0,
            "is_qty_decimal": 0,
            "is_virtual": 0,
            "item_id": 653,
            "name": "حقيبة امبوريو ارمانى",
            "no_discount": 0,
            "order_id": 361,
            "original_price": 5992.5,
            "price": 2996.25,
            "price_incl_tax": 2996.25,
            "product_id": 31985,
            "product_type": "simple",
            "qty_canceled": 0,
            "qty_invoiced": 0,
            "qty_ordered": 1,
            "qty_refunded": 0,
            "qty_shipped": 0,
            "quote_item_id": 1520,
            "row_invoiced": 0,
            "row_total": 2996.25,
            "row_total_incl_tax": 2996.25,
            "row_weight": 0.0005,
            "sku": "ar0674",
            "store_id": 2,
            "tax_amount": 0,
            "tax_invoiced": 0,
            "tax_percent": 0,
            "updated_at": "2021-03-25 06:25:33",
            "weight": 0.0005,
            "extension_attributes": {
                "mp_gift_wrap": {
                    "price": null,
                    "use_gift_message": false,
                    "gift_message": null,
                    "gift_message_fee": null,
                    "all_cart": false,
                    "wrap_id": null,
                    "name": null,
                    "status": null,
                    "price_type": null,
                    "amount": null,
                    "description": null,
                    "image": null,
                    "category": null,
                    "sort_order": null,
                    "created_at": null,
                    "updated_at": null
                }
            }
        }
    ],
    "billing_address": {
        "address_type": "billing",
        "city": "Meerut",
        "country_id": "SA",
        "email": "arpan@unyscape.com",
        "entity_id": 665,
        "firstname": "arpan test account",
        "lastname": "bhatia",
        "parent_id": 361,
        "postcode": "11501",
        "region": "New York",
        "region_code": "New York",
        "region_id": 43,
        "street": [
            "Greater Pallavpuram"
        ],
        "telephone": "(516) 555-1111"
    },
    "payment": {
        "account_status": null,
        "additional_information": [
            "Cash On Delivery (Cash On Delivery Charges: $5)",
            ""
        ],
        "amount_ordered": 3001.58,
        "base_amount_ordered": 804.33,
        "base_shipping_amount": 0,
        "cc_exp_year": "0",
        "cc_last4": null,
        "cc_ss_start_month": "0",
        "cc_ss_start_year": "0",
        "entity_id": 347,
        "method": "cashondelivery",
        "parent_id": 361,
        "shipping_amount": 0
    },
    "status_histories": [],
    "extension_attributes": {
        "shipping_assignments": [
            {
                "shipping": {
                    "address": {
                        "address_type": "shipping",
                        "city": "Meerut",
                        "country_id": "SA",
                        "email": "arpan@unyscape.com",
                        "entity_id": 664,
                        "firstname": "arpan test account",
                        "lastname": "bhatia",
                        "parent_id": 361,
                        "postcode": "11501",
                        "region": "New York",
                        "region_code": "New York",
                        "region_id": 43,
                        "street": [
                            "Greater Pallavpuram",
                            "Greater Pallavpuram"
                        ],
                        "telephone": "7906156955"
                    },
                    "method": "freeshipping_freeshipping",
                    "total": {
                        "base_shipping_amount": 0,
                        "base_shipping_discount_amount": 0,
                        "base_shipping_discount_tax_compensation_amnt": 0,
                        "base_shipping_incl_tax": 0,
                        "base_shipping_tax_amount": 0,
                        "shipping_amount": 0,
                        "shipping_discount_amount": 0,
                        "shipping_discount_tax_compensation_amount": 0,
                        "shipping_incl_tax": 0,
                        "shipping_tax_amount": 0
                    }
                },
                "items": [
                    {
                        "amount_refunded": 0,
                        "base_amount_refunded": 0,
                        "base_discount_amount": 0,
                        "base_discount_invoiced": 0,
                        "base_discount_tax_compensation_amount": 0,
                        "base_original_price": 1598,
                        "base_price": 799,
                        "base_price_incl_tax": 799,
                        "base_row_invoiced": 0,
                        "base_row_total": 799,
                        "base_row_total_incl_tax": 799,
                        "base_tax_amount": 0,
                        "base_tax_invoiced": 0,
                        "created_at": "2021-03-25 06:25:33",
                        "discount_amount": 0,
                        "discount_invoiced": 0,
                        "discount_percent": 0,
                        "free_shipping": 0,
                        "discount_tax_compensation_amount": 0,
                        "is_qty_decimal": 0,
                        "is_virtual": 0,
                        "item_id": 653,
                        "name": "حقيبة امبوريو ارمانى",
                        "no_discount": 0,
                        "order_id": 361,
                        "original_price": 5992.5,
                        "price": 2996.25,
                        "price_incl_tax": 2996.25,
                        "product_id": 31985,
                        "product_type": "simple",
                        "qty_canceled": 0,
                        "qty_invoiced": 0,
                        "qty_ordered": 1,
                        "qty_refunded": 0,
                        "qty_shipped": 0,
                        "quote_item_id": 1520,
                        "row_invoiced": 0,
                        "row_total": 2996.25,
                        "row_total_incl_tax": 2996.25,
                        "row_weight": 0.0005,
                        "sku": "ar0674",
                        "store_id": 2,
                        "tax_amount": 0,
                        "tax_invoiced": 0,
                        "tax_percent": 0,
                        "updated_at": "2021-03-25 06:25:33",
                        "weight": 0.0005,
                        "extension_attributes": {
                            "mp_gift_wrap": {
                                "price": null,
                                "use_gift_message": false,
                                "gift_message": null,
                                "gift_message_fee": null,
                                "all_cart": false,
                                "wrap_id": null,
                                "name": null,
                                "status": null,
                                "price_type": null,
                                "amount": null,
                                "description": null,
                                "image": null,
                                "category": null,
                                "sort_order": null,
                                "created_at": null,
                                "updated_at": null
                            }
                        }
                    }
                ]
            }
        ],
        "payment_additional_info": [
            {
                "key": "method_title",
                "value": "Cash On Delivery (Cash On Delivery Charges: $5)"
            },
            {
                "key": "instructions",
                "value": ""
            }
        ],
        "applied_taxes": [],
        "item_applied_taxes": [],
        "device_data": {
            "entity_id": 347,
            "device_code": null,
            "area_code": "3",
            "order_id": 361,
            "value": [
                "347",
                "361",
                null,
                "3"
            ],
            "device_name": "Desktop",
            "area_name": "Rest Api"
        },
        "payment_surcharge_amount": "5.3300",
        "base_payment_surcharge_amount": "5.3300",
        "payment_surcharge_tax_amount": "0.0000",
        "base_payment_surcharge_tax_amount": "0.0000",
        "payment_surcharge_description": "Cash On Delivery Charges"
    }
}*/

}