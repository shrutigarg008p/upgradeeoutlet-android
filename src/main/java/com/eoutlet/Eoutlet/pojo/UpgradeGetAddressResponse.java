package com.eoutlet.Eoutlet.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpgradeGetAddressResponse {

    private String response;
    private List<UpgradedGetAddressList> data = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<UpgradedGetAddressList> getData() {
        return data;
    }

    public void setData(List<UpgradedGetAddressList> data) {
        this.data = data;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
