package com.eoutlet.Eoutlet.pojo;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;



@JsonIgnoreProperties(ignoreUnknown = true)

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "label",
            "attr_id",
            "data"
    })
    public class GetSizeandColour {

        @JsonProperty("label")
        public String label;
        @JsonProperty("attr_id")
        public Integer attrId;
        @JsonProperty("data")
        public List<colorandsizedetail2> data = null;

    }



