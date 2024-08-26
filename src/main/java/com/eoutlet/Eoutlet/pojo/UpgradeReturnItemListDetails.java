package com.eoutlet.Eoutlet.pojo;

import java.util.HashMap;
import java.util.Map;

public class UpgradeReturnItemListDetails {
    private String entity_id;
    private String rma_id;
    private String order_id;
    private String updated_at;
    private String status;
    private String status_code;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(String entity_id) {
        this.entity_id = entity_id;
    }

    public String getRma_id() {
        return rma_id;
    }

    public void setRma_id(String rma_id) {
        this.rma_id = rma_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}

