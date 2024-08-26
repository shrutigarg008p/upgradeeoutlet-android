package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReorderResponse {



    @JsonProperty("msg")

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    @JsonProperty("data")

    private String data;

    public String getdata() {
        return data;
    }

    public void setdata(String data) {
        this.data = data;
    }



}
