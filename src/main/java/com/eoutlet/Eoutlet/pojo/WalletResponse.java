package com.eoutlet.Eoutlet.pojo;




import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "message",
        "increment_id"
})

public class WalletResponse {

    @JsonProperty("message")
    public String message;
    @JsonProperty("increment_id")
    public String incrementId;

}
