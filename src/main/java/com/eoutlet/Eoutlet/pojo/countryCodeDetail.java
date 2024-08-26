package com.eoutlet.Eoutlet.pojo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "code",
        "name",
        "cel_code",
        "placeholder",
        "currency",
        "flag"
})
public class countryCodeDetail {

    @JsonProperty("code")
    public String code;
    @JsonProperty("name")
    public String name;
    @JsonProperty("cel_code")
    public String cel_code;
    @JsonProperty("placeholder")
    public String placeholder;
    @JsonProperty("currency")
    public String currency;
    @JsonProperty("flag")
    public String flag;

}
