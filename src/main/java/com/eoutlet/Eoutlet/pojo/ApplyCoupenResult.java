package com.eoutlet.Eoutlet.pojo;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
public class ApplyCoupenResult {

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "msg",
            "data",
            "rule_id",
            "discount_amount",
            "type",



    })

        @JsonProperty("msg")
        public String msg;
        @JsonProperty("data")
        public List<Object> data = null;
        @JsonProperty("rule_id")
        public String ruleId;
        @JsonProperty("discount_amount")
        public String discountAmount;

        @JsonProperty("type")
          public String type;

}