
package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "type",
    "card_number",
    "expirationDate",
    "gateway_token",
    "public_hash"
})
public class CardDetail {

    @JsonProperty("id")
    public String id;
    @JsonProperty("type")
    public String type;
    @JsonProperty("card_number")
    public String cardNumber;
    @JsonProperty("expirationDate")
    public String expirationDate;
    @JsonProperty("gateway_token")
    public String gatewayToken;
    @JsonProperty("public_hash")
    public String publicHash;

}
