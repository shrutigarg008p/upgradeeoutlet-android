package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "response",
        "data",
        "message"
})

public class UpgradeLoginResponse {


    @JsonProperty("response")
        public String response;
        @JsonProperty("message")
        public String message;
        @JsonProperty("data")
        public List<Logindata> data = null;
    }


