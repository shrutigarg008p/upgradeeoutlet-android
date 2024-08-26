package com.eoutlet.Eoutlet.pojo;

import java.util.HashMap;
import java.util.Map;

public class RemoveFromWishListResponse {

    private String response=" ";


    private String msg = " ";
    private String data;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }



    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
