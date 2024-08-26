package com.eoutlet.Eoutlet.pojo;

import java.util.List;

public class CustomData {
    public String orderNumber;
    public String totalAmount;
    public String status;
    public List<CustoData2> custoData2List;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CustoData2> getCustoData2List() {
        return custoData2List;
    }

    public void setCustoData2List(List<CustoData2> custoData2List) {
        this.custoData2List = custoData2List;
    }
}
