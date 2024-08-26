package com.eoutlet.Eoutlet.pojo;

import java.util.HashMap;
import java.util.Map;

public class UpgradeReturnOrderFormResponse {
    private String response;
    private UpgradeReturnOrderFormData data;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public UpgradeReturnOrderFormData getData() {
        return data;
    }

    public void setData(UpgradeReturnOrderFormData data) {
        this.data = data;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
