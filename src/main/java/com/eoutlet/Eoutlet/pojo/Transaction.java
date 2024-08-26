package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "order_id",
        "comment",
        "amount",
        "create_at",
        "action",
        "action_flag"
})
public class Transaction {

    @JsonProperty("id")
    public String id;
    @JsonProperty("order_id")
    public String orderId;
    @JsonProperty("comment")
    public String comment;
    @JsonProperty("amount")
    public Integer amount;
    @JsonProperty("create_at")
    public String createAt;
    @JsonProperty("action")
    public String action;
    @JsonProperty("action_flag")
    public String action_flag;

}