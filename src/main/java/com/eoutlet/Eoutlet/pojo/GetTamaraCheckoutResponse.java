
package com.eoutlet.Eoutlet.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "tamara_order_id",
    "magento_order_id",
    "payment_success_redirect_url",
    "payment_cancel_redirect_url",
    "payment_failure_redirect_url",
    "redirect_url"
})

public class GetTamaraCheckoutResponse {

    @JsonProperty("tamara_order_id")
    public String tamaraOrderId;
    @JsonProperty("magento_order_id")
    public Integer magentoOrderId;
    @JsonProperty("payment_success_redirect_url")
    public String paymentSuccessRedirectUrl;
    @JsonProperty("payment_cancel_redirect_url")
    public String paymentCancelRedirectUrl;
    @JsonProperty("payment_failure_redirect_url")
    public String paymentFailureRedirectUrl;
    @JsonProperty("redirect_url")
    public String redirectUrl;

}
