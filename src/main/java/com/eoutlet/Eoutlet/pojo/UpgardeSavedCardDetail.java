
package com.eoutlet.Eoutlet.pojo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "msg",
    "data"
})
public class UpgardeSavedCardDetail {

    @JsonProperty("msg")
    public String msg;
    @JsonProperty("data")
    public List<CardDetail> data = null;

}
