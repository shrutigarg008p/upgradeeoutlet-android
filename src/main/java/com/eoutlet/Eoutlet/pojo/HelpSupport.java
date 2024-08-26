package com.eoutlet.Eoutlet.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HelpSupport {


        @SerializedName("msg")
        @Expose
        private String msg;
        @SerializedName("data")
        @Expose
        private List<DatumHelp> data = null;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public List<DatumHelp> getData() {
            return data;
        }

        public void setData(List<DatumHelp> data) {
            this.data = data;
        }

}
