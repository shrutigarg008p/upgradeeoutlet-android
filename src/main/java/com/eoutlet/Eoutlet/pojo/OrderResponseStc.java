package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;



@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "msg",
        "data",
        "order_id",
        "payment_method",
        "OtpReference",
        "STCPayPmtReference",
        "stcmobile"
})
public class OrderResponseStc {

    @JsonProperty("msg")
    public String msg;
    @JsonProperty("data")
    public Object data = null;
    @JsonProperty("order_id")
    public String orderId;
    @JsonProperty("payment_method")
    public String paymentMethod;
    @JsonProperty("OtpReference")
    public String otpReference;
    @JsonProperty("STCPayPmtReference")
    public String sTCPayPmtReference;
    @JsonProperty("stcmobile")
    public String stcmobile;

}

