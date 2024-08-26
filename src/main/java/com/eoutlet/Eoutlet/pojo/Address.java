
package com.eoutlet.Eoutlet.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "region",
    "region_id",
    "region_code",
    "country_id",
    "street",
    "telephone",
    "postcode",
    "city",
    "firstname",
    "lastname",
    "customer_id",
    "email",
    "same_as_billing",
    "save_in_address_book"
})
public class Address {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("region")
    public Object region;
    @JsonProperty("region_id")
    public Object regionId;
    @JsonProperty("region_code")
    public Object regionCode;
    @JsonProperty("country_id")
    public Object countryId;
    @JsonProperty("street")
    public List<String> street = null;
    @JsonProperty("telephone")
    public String telephone;
    @JsonProperty("postcode")
    public String postcode;
    @JsonProperty("city")
    public Object city;
    @JsonProperty("firstname")
    public Object firstname;
    @JsonProperty("lastname")
    public Object lastname;
    @JsonProperty("customer_id")
    public Integer customerId;
    @JsonProperty("email")
    public String email;
    @JsonProperty("same_as_billing")
    public Integer sameAsBilling;
    @JsonProperty("save_in_address_book")
    public Integer saveInAddressBook;

}
