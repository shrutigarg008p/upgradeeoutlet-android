package com.eoutlet.Eoutlet.pojo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "address_id",
        "firstname",
        "lastname",
        "city",
        "street",
        "telephone",
        "country",
        "default_billing",
        "default_shipping",
        "country_id"
})
public class AddressDetail {






    @JsonProperty("address_id")
    public String addressId;
    @JsonProperty("firstname")
    public String firstname;
    @JsonProperty("lastname")
    public String lastname;
    @JsonProperty("city")
    public String city;
    @JsonProperty("street")
    public String street;
    @JsonProperty("telephone")
    public String telephone;
    @JsonProperty("country")
    public String country;
    @JsonProperty("default_billing")
    public Integer defaultBilling;
    @JsonProperty("default_shipping")
    public Integer defaultShipping;
    @JsonProperty("country_id")
    public String country_id;

}
