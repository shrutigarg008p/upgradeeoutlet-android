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
        "mob",
        "country_code",
        "active",
        "wallet_active",
        "login_type"
})
public class Logindata {




    @JsonProperty("id")
    public String id;
    @JsonProperty("email")
    public String email;
    @JsonProperty("fname")
    public String fname;
    @JsonProperty("lname")
    public String lname;
    @JsonProperty("mob")
    public String mob;
    @JsonProperty("active")
    public String active;
    @JsonProperty("wallet_active")
    public String wallet_active;
    @JsonProperty("country_code")
    public String country_code;
    @JsonProperty("login_type")
    public String login_type;

}
