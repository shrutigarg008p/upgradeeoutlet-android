package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpgradedHomeScreenResponse {

    private String response;
    private UpgradedHomeScreenData data;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public UpgradedHomeScreenData getData() {
        return data;
    }

    public void setData(UpgradedHomeScreenData data) {
        this.data = data;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}