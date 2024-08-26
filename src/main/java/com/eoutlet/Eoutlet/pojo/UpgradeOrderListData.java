package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpgradeOrderListData {
    private String order_id;
    private String increment_id;
    private String status;
    private String status_code;
    private Double grand_total;
    private String created_at;
    private Integer shipping_amount;


    private String currency;
    private List<UpgradeOrderListProductData> products = null;
    private Integer canreturn;
    private String tracking_url;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getIncrement_id() {
        return increment_id;
    }

    public void setIncrement_id(String increment_id) {
        this.increment_id = increment_id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public Double getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(Double grand_total) {
        this.grand_total = grand_total;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Integer getShipping_amount() {
        return shipping_amount;
    }

    public void setShipping_amount(Integer shipping_amount) {
        this.shipping_amount = shipping_amount;
    }

    public List<UpgradeOrderListProductData> getProducts() {
        return products;
    }

    public void setProducts(List<UpgradeOrderListProductData> products) {
        this.products = products;
    }

    public Integer getCanreturn() {
        return canreturn;
    }

    public void setCanreturn(Integer canreturn) {
        this.canreturn = canreturn;
    }

    public String getTracking_url() {
        return tracking_url;
    }

    public void setTracking_url(String tracking_url) {
        this.tracking_url = tracking_url;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


}
