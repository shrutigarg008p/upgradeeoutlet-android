package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "amount",
        "response_code",
        "card_number",
        "card_holder_name",
        "signature",
        "merchant_identifier",
        "access_code",
        "payment_option",
        "expiry_date",
        "customer_ip",
        "language",
        "eci",
        "fort_id",
        "command",
        "3ds_url",
        "response_message",
        "merchant_reference",
        "customer_email",
        "currency",
        "customer_name",
        "status",
        "order_id"
})

public class OrderResponse {


    @JsonProperty("amount")
    public String amount;
    @JsonProperty("response_code")
    public String responseCode;
    @JsonProperty("card_number")
    public String cardNumber;
    @JsonProperty("card_holder_name")
    public String cardHolderName;
    @JsonProperty("signature")
    public String signature;
    @JsonProperty("merchant_identifier")
    public String merchantIdentifier;
    @JsonProperty("access_code")
    public String accessCode;
    @JsonProperty("payment_option")
    public String paymentOption;
    @JsonProperty("expiry_date")
    public String expiryDate;
    @JsonProperty("customer_ip")
    public String customerIp;
    @JsonProperty("language")
    public String language;
    @JsonProperty("eci")
    public String eci;
    @JsonProperty("fort_id")
    public String fortId;
    @JsonProperty("command")
    public String command;
    @JsonProperty("3ds_url")
    public String _3dsUrl;
    @JsonProperty("response_message")
    public String responseMessage;
    @JsonProperty("merchant_reference")
    public String merchantReference;
    @JsonProperty("customer_email")
    public String customerEmail;
    @JsonProperty("currency")
    public String currency;
    @JsonProperty("customer_name")
    public String customerName;
    @JsonProperty("status")
    public String status;
    @JsonProperty("order_id")
    public String orderId;




}
