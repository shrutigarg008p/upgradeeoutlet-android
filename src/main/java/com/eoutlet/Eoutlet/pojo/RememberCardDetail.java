package com.eoutlet.Eoutlet.pojo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "card_number",
        "expiry_date",
        "token_name"
})
public class RememberCardDetail {

    @JsonProperty("name")
    public String name;
    @JsonProperty("card_number")
    public String cardNumber;
    @JsonProperty("expiry_date")
    public String expiryDate;
    @JsonProperty("token_name")
    public String tokenName;

}
