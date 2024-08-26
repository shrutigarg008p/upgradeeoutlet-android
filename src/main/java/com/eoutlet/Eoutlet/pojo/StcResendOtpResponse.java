package com.eoutlet.Eoutlet.pojo;


import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "msg",
        "data",
        "order_id",
        "OtpReference",
        "STCPayPmtReference",
        "stcmobile"
})

public class StcResendOtpResponse {
    @JsonProperty("msg")
    public String msg;
    @JsonProperty("data")
    public List<Object> data = null;
    @JsonProperty("order_id")
    public String orderId;
    @JsonProperty("OtpReference")
    public Object otpReference;
    @JsonProperty("STCPayPmtReference")
    public Object sTCPayPmtReference;
    @JsonProperty("stcmobile")
    public String stcmobile;


}
