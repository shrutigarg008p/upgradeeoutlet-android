
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
    "base_currency_symbol",
    "default_display_currency_code",
    "default_display_currency_symbol",
    "available_currency_codes",
    "exchange_rates"
})
public class GetCurrencyDetail {

    @JsonProperty("base_currency_code")
    public String baseCurrencyCode;
    @JsonProperty("base_currency_symbol")
    public String baseCurrencySymbol;
    @JsonProperty("default_display_currency_code")
    public String defaultDisplayCurrencyCode;
    @JsonProperty("default_display_currency_symbol")
    public String defaultDisplayCurrencySymbol;
    @JsonProperty("available_currency_codes")
    public List<String> availableCurrencyCodes = null;
    @JsonProperty("exchange_rates")
    public List<ExchangeRate> exchangeRates = null;

}
