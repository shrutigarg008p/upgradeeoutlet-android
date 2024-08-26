
package com.eoutlet.Eoutlet.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "response",
    "data"
})

public class ResetPasswordresponse {

    @JsonProperty("response")
    public String response;
    @JsonProperty("data")
    public Object data;

}
