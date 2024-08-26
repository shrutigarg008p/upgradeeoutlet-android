package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "response_code",
        "device_id",
        "response_message",
        "service_command",
        "sdk_token",
        "signature",
        "merchant_identifier",
        "access_code",
        "language",
        "status",
        "token"
})
public class GetToken {

    @JsonProperty("response_code")
    public String responseCode;
    @JsonProperty("device_id")
    public String deviceId;
    @JsonProperty("response_message")
    public String responseMessage;
    @JsonProperty("service_command")
    public String serviceCommand;
    @JsonProperty("sdk_token")
    public String sdkToken;
    @JsonProperty("signature")
    public String signature;
    @JsonProperty("merchant_identifier")
    public String merchantIdentifier;
    @JsonProperty("access_code")
    public String accessCode;
    @JsonProperty("language")
    public String language;
    @JsonProperty("status")
    public String status;
    @JsonProperty("token")
    public String token;

}