
package com.eoutlet.Eoutlet.pojo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "is_enabled",
    "code",
    "amount",
    "title",
    "transaction"
})
public class UpgradedData {

    @JsonProperty("is_enabled")
    public String isEnabled;
    @JsonProperty("code")
    public String code;
    @JsonProperty("amount")
    public Integer amount;
    @JsonProperty("title")
    public String title;
    @JsonProperty("transaction")
    public List<UpgradedTransaction> transaction = null;

}
