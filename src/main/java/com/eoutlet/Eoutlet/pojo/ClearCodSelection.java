
package com.eoutlet.Eoutlet.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "errors",
    "message",
    "title"
})

public class ClearCodSelection {

    @JsonProperty("errors")
    public Boolean errors;
    @JsonProperty("message")
    public String message;
    @JsonProperty("title")
    public Object title;

}
