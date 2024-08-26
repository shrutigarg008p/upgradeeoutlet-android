package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "msg",
        "message"
})
public class StcRetainApi {






    @JsonProperty("msg")
    public String msg;
    @JsonProperty("message")
    public String message;

}
