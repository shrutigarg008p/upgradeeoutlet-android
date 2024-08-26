
package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "global_currency_code",
    "base_currency_code",
    "store_currency_code",
    "quote_currency_code",
    "store_to_base_rate",
    "store_to_quote_rate",
    "base_to_global_rate",
    "base_to_quote_rate"
})
public class Currency {

    @JsonProperty("global_currency_code")
    public String globalCurrencyCode;
    @JsonProperty("base_currency_code")
    public String baseCurrencyCode;
    @JsonProperty("store_currency_code")
    public String storeCurrencyCode;
    @JsonProperty("quote_currency_code")
    public String quoteCurrencyCode;
    @JsonProperty("store_to_base_rate")
    public Integer storeToBaseRate;
    @JsonProperty("store_to_quote_rate")
    public Integer storeToQuoteRate;
    @JsonProperty("base_to_global_rate")
    public Integer baseToGlobalRate;
    @JsonProperty("base_to_quote_rate")
    public Integer baseToQuoteRate;

}
