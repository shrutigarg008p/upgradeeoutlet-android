
package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "code",
    "title",
    "value",
    "extension_attributes",
    "area"
})
public class TotalSegment {

    @JsonProperty("code")
    public String code;
    @JsonProperty("title")
    public String title;
    @JsonProperty("value")
    public Float value;
    @JsonProperty("extension_attributes")
    public ExtensionAttributes2 extensionAttributes;
    @JsonProperty("area")
    public String area;

}
