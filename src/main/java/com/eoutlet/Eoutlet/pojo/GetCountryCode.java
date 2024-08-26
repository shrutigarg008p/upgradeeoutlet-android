package com.eoutlet.Eoutlet.pojo;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.firebase.encoders.annotations.Encodable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "response",
        "data",

})
public class GetCountryCode {

    @JsonProperty("response")
    public String response;

    @JsonProperty("data")
    public List<countryCodeDetail> data = null;


}
