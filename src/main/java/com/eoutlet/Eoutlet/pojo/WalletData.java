package com.eoutlet.Eoutlet.pojo;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customer_id",
        "wallet_amount",
        "transactions",
        "is_enabled",
        "amount",
        "code",
        "title"
})
public class WalletData {

    @JsonProperty("customer_id")
    public String customerId;
    @JsonProperty("wallet_amount")
    public Integer walletAmount;
    @JsonProperty("transactions")
    public List<Transaction> transactions = null;
    @JsonProperty("is_enabled")
    public String is_enabled;

    @JsonProperty("amount")
    public String amount;
    @JsonProperty("code")
    public String code;
    @JsonProperty("title")
    public String title;



}
