package com.eoutlet.Eoutlet.pojo;

import java.util.List;

public class ReturnItemViewClickReturnItem {
    private String name;
    private String sku;
    private String image;
    private List<ReturnItemViewClickReturnItemOptions> options = null;
    private String reason;
    private String condition;
    private String resolution;
    private Integer qty_requested;
    private Object qty_authorized;
    private Object qty_received;
    private Object qty_approved;
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<ReturnItemViewClickReturnItemOptions> getOptions() {
        return options;
    }

    public void setOptions(List<ReturnItemViewClickReturnItemOptions> options) {
        this.options = options;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public Integer getQty_requested() {
        return qty_requested;
    }

    public void setQty_requested(Integer qty_requested) {
        this.qty_requested = qty_requested;
    }

    public Object getQty_authorized() {
        return qty_authorized;
    }

    public void setQty_authorized(Object qty_authorized) {
        this.qty_authorized = qty_authorized;
    }

    public Object getQty_received() {
        return qty_received;
    }

    public void setQty_received(Object qty_received) {
        this.qty_received = qty_received;
    }

    public Object getQty_approved() {
        return qty_approved;
    }

    public void setQty_approved(Object qty_approved) {
        this.qty_approved = qty_approved;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
