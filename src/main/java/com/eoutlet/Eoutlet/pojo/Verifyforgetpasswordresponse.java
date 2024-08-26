package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*{"success":true,"otp":"2404","customerId":"122042","url":"https:\/\/upgrade.eoutlet.com\/customer\/account\/"}*/
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Verifyforgetpasswordresponse {


    @JsonProperty("success")
    public Boolean success;
    @JsonProperty("otp")
    public String otp;
    @JsonProperty("customerId")
    public String customerId;
    @JsonProperty("url")
    public String url;

    @JsonProperty("msg")
    public String msg;


}
