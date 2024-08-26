
package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "email",
        "fname",
        "lname",
        "country_code",
        "mob",
        "active",
        "wallet_active",
        "newsletter"
})
public class UserData {

    @JsonProperty("id")
    public String id;
    @JsonProperty("email")
    public String email;
    @JsonProperty("fname")
    public String fname;
    @JsonProperty("lname")
    public String lname;
    @JsonProperty("country_code")
    public String countryCode;
    @JsonProperty("mob")
    public String mob;
    @JsonProperty("active")
    public String active;
    @JsonProperty("wallet_active")
    public String walletActive;
    @JsonProperty("newsletter")
    public Boolean newsletter;

}
