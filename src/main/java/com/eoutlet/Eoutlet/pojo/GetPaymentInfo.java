
package com.eoutlet.Eoutlet.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "payment_methods",
    "totals"
})
public class GetPaymentInfo {

    @JsonProperty("payment_methods")
    public List<PaymentMethod> paymentMethods = null;
    @JsonProperty("totals")
    public Totals totals;

}
