
package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "carrier_code",
    "method_code",
    "carrier_title",
    "method_title",
    "amount",
    "base_amount",
    "available",
    "error_message",
    "price_excl_tax",
    "price_incl_tax"
})
public class GetShippingMethod {

    @JsonProperty("carrier_code")
    public String carrierCode;
    @JsonProperty("method_code")
    public String methodCode;
    @JsonProperty("carrier_title")
    public String carrierTitle;
    @JsonProperty("method_title")
    public String methodTitle;
    @JsonProperty("amount")
    public Integer amount;
    @JsonProperty("base_amount")
    public Integer baseAmount;
    @JsonProperty("available")
    public Boolean available;
    @JsonProperty("error_message")
    public String errorMessage;
    @JsonProperty("price_excl_tax")
    public Integer priceExclTax;
    @JsonProperty("price_incl_tax")
    public Integer priceInclTax;

}
