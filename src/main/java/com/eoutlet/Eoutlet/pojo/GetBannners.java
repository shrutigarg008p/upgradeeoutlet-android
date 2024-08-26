package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "msg",
        "data",
        "slidingbanners"
})

public class GetBannners {

    @JsonProperty("msg")
    public String msg;
    @JsonProperty("data")
    public List<ImagesDetail> data = null;
    @JsonProperty("slidingbanners")
    public List<Slidebanner> slidingbanners = null;

}







