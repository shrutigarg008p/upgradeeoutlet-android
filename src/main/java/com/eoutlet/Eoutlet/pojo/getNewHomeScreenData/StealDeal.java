
package com.eoutlet.Eoutlet.pojo.getNewHomeScreenData;

import com.eoutlet.Eoutlet.pojo.Attribute;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "image",
        "id",
        "name",
        "caption"
})
public class StealDeal implements Serializable {

    @JsonProperty("image")
    public String image;
    @JsonProperty("id")
    public String id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("caption")
    public String caption;
    @JsonProperty("attribute")
    public Attribute attribute;
}
