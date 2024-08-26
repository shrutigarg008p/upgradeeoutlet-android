
package com.eoutlet.Eoutlet.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "response",
    "data"
})

public class GetScreolledHomePageData {

    @JsonProperty("response")
    private String response;
    @JsonProperty("data")
    private ScrolledHomeData data;

    @JsonProperty("response")
    public String getResponse() {
        return response;
    }

    @JsonProperty("response")
    public void setResponse(String response) {
        this.response = response;
    }

    @JsonProperty("data")
    public ScrolledHomeData getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(ScrolledHomeData data) {
        this.data = data;
    }

}
