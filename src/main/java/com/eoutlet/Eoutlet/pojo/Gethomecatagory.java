
package com.eoutlet.Eoutlet.pojo;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "msg",
        "data",
        "slidingbanners"
})
public class Gethomecatagory {

    @JsonProperty("msg")
    public String msg;
    @JsonProperty("data")
    public List<Datum> data = null;
    @JsonProperty("slidingbanners")
    public List<Slidingbanners> slidingbanners = null;

}