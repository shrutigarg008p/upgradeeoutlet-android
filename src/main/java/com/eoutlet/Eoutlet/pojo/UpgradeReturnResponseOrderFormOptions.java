package com.eoutlet.Eoutlet.pojo;

import java.util.HashMap;
import java.util.Map;

public class UpgradeReturnResponseOrderFormOptions {
    private String label;
    private String value;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
