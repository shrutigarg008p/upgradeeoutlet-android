package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "order_id",
        "total_amount","increment_id","status","display_status","items","increment_order_id","tracking_url","created_at"




})


public class OrderListItem implements Serializable {

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("increment_order_id")
    private String incrementorderid;
    @JsonProperty("total_amount")
    private Integer totalAmount;
    @JsonProperty("increment_id")
    private String incrementId;


    @JsonProperty("tracking_url")
    private String tracking_url;


    @JsonProperty("status")
    private String status;
    @JsonProperty("display_status")
    private String display_status;
    @JsonProperty("created_at")
    private String created_at;




    @JsonProperty("items")

    private List<OrderChildItem> items = null;


    public String getTracking_url() {
        return tracking_url;
    }

    public void setTracking_url(String tracking_url) {
        this.tracking_url = tracking_url;
    }



    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getIncrementId() {
        return incrementId;
    }
    public String getIncrementOrderId() {
        return incrementorderid;
    }
    public void setIncrementId(String incrementId) {
        this.incrementId = incrementId;
    }

    public String   getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String   getdisplaystatusStatus() {
        return display_status;
    }

    public void setdisplaystatusStatus(String status) {
        this.display_status = display_status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void getCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public List<OrderChildItem> getItems() {
        return items;
    }

    public void setItems(List<OrderChildItem> items) {
        this.items = items;
    }
}
