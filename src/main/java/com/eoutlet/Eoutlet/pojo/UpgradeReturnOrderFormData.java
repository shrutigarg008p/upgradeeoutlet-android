package com.eoutlet.Eoutlet.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpgradeReturnOrderFormData {

    private String order_id;
    private String customer_name;
    private String customer_email;
    private UpgradeReturnOrderFormCustomerData customer_address;
    private List<UpgradeReturnResponseFormOrderItems> orderitems = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
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

    public UpgradeReturnOrderFormCustomerData getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(UpgradeReturnOrderFormCustomerData customer_address) {
        this.customer_address = customer_address;
    }

    public List<UpgradeReturnResponseFormOrderItems> getOrderitems() {
        return orderitems;
    }

    public void setOrderitems(List<UpgradeReturnResponseFormOrderItems> orderitems) {
        this.orderitems = orderitems;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
