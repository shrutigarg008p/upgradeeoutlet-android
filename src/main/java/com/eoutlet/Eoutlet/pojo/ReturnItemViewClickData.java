package com.eoutlet.Eoutlet.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReturnItemViewClickData {

    private String rma_id;
    private String status;
    private String order_id;
    private String created_at;
    private String customer_name;
    private String customer_email;
    private ReturnItemViewClickCustomerAddress customer_address;
    private List<ReturnItemViewClickReturnItem> returnitems = null;
    private List<ReturnItemViewClickMessages> messages = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getRma_id() {
        return rma_id;
    }

    public void setRma_id(String rma_id) {
        this.rma_id = rma_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public ReturnItemViewClickCustomerAddress getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(ReturnItemViewClickCustomerAddress customer_address) {
        this.customer_address = customer_address;
    }

    public List<ReturnItemViewClickReturnItem> getReturnitems() {
        return returnitems;
    }

    public void setReturnitems(List<ReturnItemViewClickReturnItem> returnitems) {
        this.returnitems = returnitems;
    }

    public List<ReturnItemViewClickMessages> getMessages() {
        return messages;
    }

    public void setMessages(List<ReturnItemViewClickMessages> messages) {
        this.messages = messages;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
