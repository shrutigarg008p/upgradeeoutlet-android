package com.eoutlet.Eoutlet.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpgradeReturnItemListResponse {
    private String response;
    private List<UpgradeReturnItemListDetails> data = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<UpgradeReturnItemListDetails> getData() {
        return data;
    }

    public void setData(List<UpgradeReturnItemListDetails> data) {
        this.data = data;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
