package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ElasticSearchResponse {

    public String msg;
    public String response;
    public String query;
    public List<ElasticSearchData> data = null;
    public Integer page;
}
