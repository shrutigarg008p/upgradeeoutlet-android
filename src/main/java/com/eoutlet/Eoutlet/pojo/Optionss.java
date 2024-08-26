package com.eoutlet.Eoutlet.pojo;




import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "color",
        "size"
})
public class Optionss {

    @JsonProperty("color")
    public Color color;
    @JsonProperty("size")
    public Size size;

}